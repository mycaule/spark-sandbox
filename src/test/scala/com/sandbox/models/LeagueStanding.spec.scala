package com.sandbox.models

import org.scalatest._
import org.slf4j.LoggerFactory

import LeagueStanding._

class LeagueStandingSpec extends FlatSpec with Matchers {
  implicit val logger = LoggerFactory.getLogger(getClass)

  "The LeagueStanding object" should "provide a way to configure indices of HTML columns to map with the case class" in {
    mapColumns(List(-1, -1, 0, 1, 9, -1, 3, 4, 5, 6, 7, -1)).toSet shouldBe Map(
      "league" -> -1,
      "season" -> -1,
      "position" -> 0,
      "team" -> 1,
      "points" -> 9,
      "played" -> -1,
      "won" -> 3,
      "drawn" -> 4,
      "lost" -> 5,
      "goalsFor" -> 6,
      "goalsAgainst" -> 7,
      "goalsDifference" -> -1
    ).toSet

    mapColumns(List(-1, -1, 0, 1, 2, -1, 4, 5, 6, 7, 8, -1)).toSet shouldBe Map(
      "league" -> -1,
      "season" -> -1,
      "position" -> 0,
      "team" -> 1,
      "points" -> 2,
      "played" -> -1,
      "won" -> 4,
      "played" -> -1,
      "drawn" -> 5,
      "lost" -> 6,
      "goalsFor" -> 7,
      "goalsAgainst" -> 8,
      "goalsDifference" -> -1
    ).toSet
  }

  it should "fail in case of error" in {
    fetch(2018, "Groland", "https://fake_url_championship").size shouldBe 0
  }

  it should "get championships standings from Wikipedia" in {
    fetch(2006, "Liga", "https://fr.wikipedia.org/wiki/Championnat_d'Espagne_de_football_2005-2006").size shouldBe 20
    // Special cases below
    fetch(1978, "Premier League", "https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1977-1978").size shouldBe 22
    fetch(2005, "Serie A", "https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2004-2005").size shouldBe 20
  }
}
