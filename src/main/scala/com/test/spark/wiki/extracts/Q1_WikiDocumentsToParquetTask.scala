package com.test.spark.wiki.extracts

import java.net.URL

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
              year + 1 -> (input.name, input.url.format(year, year + 1))
          }
      }
      .flatMap {
        case (season, (league, url)) =>
          implicit class StringImprovements(s: String) {
            import scala.util.Try
            def tryToInt = Try(s.toInt).toOption.getOrElse(-1)
          }

          try {
            val doc = Jsoup.connect(url).get
            val table = doc.select("caption:contains(Classement)").first().parent()
            val rows = table.select("tr")
            val tds = rows.head.select("td")
            println(tds.get(0).text.toInt)

            for (row <- rows) yield {
              val tds = row.select("td")
              val position = tds.get(0).text.tryToInt
              val team = tds.get(1).text
              val points = tds.get(2).text.tryToInt
              val played = tds.get(3).text.tryToInt
              val won = tds.get(4).text.tryToInt
              val drawn = tds.get(5).text.tryToInt
              val lost = tds.get(6).text.tryToInt
              val goalsFor = tds.get(7).text.tryToInt
              val goalsAgainst = tds.get(8).text.tryToInt
              val goalsDifference = tds.get(9).text.tryToInt
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
