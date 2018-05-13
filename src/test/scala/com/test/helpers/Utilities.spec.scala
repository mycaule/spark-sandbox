package com.test.helpers

import org.scalatest._
import Utilities._

class UtilitiesSpec extends FlatSpec with Matchers {
  "The Utilities object" should "convert string to int with default values" in {
    "6".tryToInt() shouldBe 6
    "???".tryToInt(42) shouldBe 42
  }

  it should "strip successive suffixes" in {
    "A B C D".stripSuffixes(List(" D")) shouldBe "A B C"
    "A B C D".stripSuffixes(List(" D", " C")) shouldBe "A B"
  }

  it should "remove successive patterns" in {
    "0 1 2 3 4 5 6 7 8 9".removeAll(List("0 ", " 2", " 4", " 6", " 8")) shouldBe "1 3 5 7 9"
    "0 1 2 3 4 5 6 7 8 9".removeAll(List(" 1", " 3", " 5", " 7", " 9")) shouldBe "0 2 4 6 8"
  }
}
