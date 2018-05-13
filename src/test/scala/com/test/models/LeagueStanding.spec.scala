package com.test.models

import org.scalatest._
import org.slf4j.LoggerFactory

class LeagueStandingSpec extends FlatSpec with Matchers {
  implicit val logger = LoggerFactory.getLogger(getClass)

  "The LeagueStanding object" should "get championships standings from Wikipedia" in {
    LeagueStanding.get(2006, "Liga", "https://fr.wikipedia.org/wiki/Championnat_d'Espagne_de_football_2005-2006").size shouldBe 20
    // Special cases below
    LeagueStanding.get(1978, "Premier League", "https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1977-1978").size shouldBe 22
    LeagueStanding.get(2005, "Serie A", "https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2004-2005").size shouldBe 20
  }

  it should "fail in case of error" in {
    LeagueStanding.get(2018, "Groland", "https://fake_url_championship").size shouldBe 0
  }
}
