package com.test.helpers

import java.text.Normalizer
import java.net.URLDecoder

object Utilities {
  implicit class StringImprovements(s: String) {
    def tryToInt(default: Int = -9999) = scala.util.Try(s.toInt).toOption.getOrElse(default)

    def stripURL = s.replace("/wiki/", "")

    def decode = URLDecoder.decode(s, "UTF-8")

    def normalize =
      Normalizer.normalize(s, Normalizer.Form.NFD)
        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
        .toUpperCase

    def stripSuffixes(tokens: List[String]) =
      tokens.foldLeft(s)((acc, cur) => acc.stripSuffix(cur))

    def removeAll(tokens: List[String]) =
      tokens.foldLeft(s)((acc, cur) => acc.replace(cur, ""))
  }
}
