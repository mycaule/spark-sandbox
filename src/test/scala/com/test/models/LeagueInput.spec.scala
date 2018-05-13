package com.test.models

import org.scalatest._

class LeagueInputSpec extends FlatSpec with Matchers {
  "The LeagueInput object" should "convert string to int with default values" in {
    LeagueInput.getLeagues().size shouldBe 5
  }
}
