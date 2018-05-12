package com.test.spark.wiki.extracts

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.apache.spark.sql.{ SaveMode, SparkSession }
import org.joda.time.DateTime
import org.jsoup.Jsoup
import org.slf4j.{ Logger, LoggerFactory }

import scala.collection.JavaConversions._

case class Q1_WikiDocumentsToParquetTask(bucket: String) extends Runnable {
  private val session: SparkSession = SparkSession.builder()
    .appName("Wiki Documents")
    .getOrCreate()

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  import session.implicits._

  override def run(): Unit = {
    val toDate = DateTime.now().withYear(2017)
    val fromDate = toDate.minusYears(40)

    getLeagues
      .toDS()
      .flatMap {
        input =>
          (fromDate.getYear until toDate.getYear).map {
            year =>
              (year + 1, (input.name, input.url.format(year, year + 1)))
          }
      }
      .flatMap {
        case (season, (league, url)) =>
          implicit class StringImprovements(s: String) {
            import scala.util.Try
            def tryToInt(default: Int = -9999) = Try({
              s.toInt
            })
              .toOption
              .getOrElse(default)
          }

          val ind = if (league == "Premier League" && List(1978, 1980, 1988).contains(season))
            Map(
              "position" -> 0,
              "team" -> 1,
              "points" -> 9,
              "won" -> 3,
              "drawn" -> 4,
              "lost" -> 5,
              "goalsFor" -> 6,
              "goalsAgainst" -> 7
            )
          else
            Map(
              "position" -> 0,
              "team" -> 1,
              "points" -> 2,
              "won" -> 4,
              "drawn" -> 5,
              "lost" -> 6,
              "goalsFor" -> 7,
              "goalsAgainst" -> 8
            )

          try {
            val doc = Jsoup.connect(url).get

            val caption = doc.select("caption:contains(Classement)")
            val wikitable = doc.select("table.wikitable")

            val table = if (caption.size > 0) caption.first().parent else wikitable.first()

            val rows = table.select("tr")
            for {
              row <- rows.tail
              tds = row.select("td")
              if tds.size > ind.values.max
            } yield {
              val position = tds(ind("position")).text
                .stripSuffix(".")
                .stripSuffix("er")
                .stripSuffix("e")
                .tryToInt()

              val team = tds(ind("team")).text
                .stripSuffix(" L")
                .stripSuffix(" S")
                .stripSuffix(" MC")
                .stripSuffix(" (CI)")
                .stripSuffix(" C3")
                .stripSuffix(" C2")
                .stripSuffix(" C1")
                .stripSuffix("'C")
                .stripSuffix(" C")
                .stripSuffix("'T")
                .stripSuffix(" T")
                .stripSuffix("P")
                // .stripSuffix("S")
                .stripSuffix("[1]")
                .stripSuffix("[2]")
                .stripSuffix("[3]")
                .stripSuffix("[4]")
                .stripSuffix("[5]")
                .stripSuffix("[6]")
                .stripSuffix("[N 2],")
                .stripSuffix(" (*)")
                .stripSuffix(" (V)")
                .stripSuffix(" *")
                .stripSuffix(" SU")
                .trim

              val points = tds(ind("points")).text
                .stripSuffix("-1")
                .stripSuffix("-2")
                .stripSuffix("A")
                .stripSuffix("**")
                .stripSuffix("*")
                .stripSuffix("[1]")
                .tryToInt()

              val won = tds(ind("won")).text.tryToInt()
              val drawn = tds(ind("drawn")).text.tryToInt()
              val lost = tds(ind("lost")).text.tryToInt()
              val played = won + drawn + lost
              val goalsFor = tds(ind("goalsFor")).text.tryToInt()
              val goalsAgainst = tds(ind("goalsAgainst")).text.tryToInt()
              val goalsDifference = goalsFor - goalsAgainst
              LeagueStanding(league, season, position, team, points, played, won, drawn, lost, goalsFor, goalsAgainst, goalsDifference)
            }
          } catch {
            case _: Throwable =>
              logger.warn(s"Can't parse season $season from $url")
              Seq.empty
          }
      }
      .repartition(2)
      .write
      .mode(SaveMode.Overwrite)
      .parquet(bucket)
  }

  private def getLeagues: Seq[LeagueInput] = {
    val mapper = new ObjectMapper(new YAMLFactory())
    val inputStream = new java.io.FileInputStream("src/main/resources/leagues.yaml")
    mapper.readValue(inputStream, classOf[Array[LeagueInput]]).toSeq
  }
}

case class LeagueInput(
    @JsonProperty("name") name: String,
    @JsonProperty("url") url: String)

case class LeagueStanding(
    league: String,
    season: Int,
    position: Int,
    team: String,
    points: Int,
    played: Int,
    won: Int,
    drawn: Int,
    lost: Int,
    goalsFor: Int,
    goalsAgainst: Int,
    goalsDifference: Int)
