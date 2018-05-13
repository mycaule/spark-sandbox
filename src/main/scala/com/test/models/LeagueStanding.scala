package com.test
package models

import org.slf4j.Logger
import org.jsoup.Jsoup
import scala.collection.JavaConversions._

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
    goalsDifference: Int
)

object LeagueStanding {
  import helpers.Utilities._

  private def extractFieldNames[T <: Product: Manifest] = {
    implicitly[Manifest[T]].runtimeClass.getDeclaredFields.map(_.getName)
  }

  // https://stackoverflow.com/questions/1226555/case-class-to-map-in-scala
  def mapColumns(list: List[Int]): Map[String, Int] = {
    val cc = LeagueStanding("", 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0)
    cc.getClass.getDeclaredFields.map(_.getName).zip(list).toMap
  }

  def fetch(season: Int, league: String, url: String)(implicit logger: Logger) = {
    val ind = if (league == "Premier League" && List(1978, 1980, 1988).contains(season))
      mapColumns(List(-1, -1, 0, 1, 9, -1, 3, 4, 5, 6, 7, -1))
    else
      mapColumns(List(-1, -1, 0, 1, 2, -1, 4, 5, 6, 7, 8, -1))

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
          .stripSuffixes(List(".", "er", "e"))
          .tryToInt()

        val team = tds(ind("team")).text
          .stripSuffixes(List(" L", " S", " MC", " (CI)", " C3", " C2",
            " C1", "'C", " C", "T,C,L", "'T", " T", "P", "[1]", "[2]", "[3]", "[4]",
            "[5]", "[6]", "[N 2],", " (*)", " (V)", " *", " SU", " -8", " -9", " CMC",
            " T S", "[N 1],", " CFC", " CL", " LDC"))
          .trim

        val points = tds(ind("points")).text
          .stripSuffixes(List("-1", "-2", "A", "**", "*", "[1]"))
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
        println(s"Can't parse season $season from $url")
        Seq.empty
    }
  }
}
