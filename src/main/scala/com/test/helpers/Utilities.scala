package com.test.helpers

object Utilities {
  implicit class StringImprovements(s: String) {
    def tryToInt(default: Int = -9999) = scala.util.Try(s.toInt).toOption.getOrElse(default)

    def stripSuffixes(tokens: List[String]) =
      tokens.foldLeft(s)((acc, cur) => acc.stripSuffix(cur))
  }
}
