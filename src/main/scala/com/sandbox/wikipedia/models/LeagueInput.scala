package com.sandbox
package wikipedia
package models

import cats.syntax.either._
import io.circe.{ Error, yaml }
import io.circe.generic.auto._

case class LeagueInput(name: String, url: String)

object LeagueInput {
  def getLeagues(path: String = "src/main/resources/leagues.yaml"): Seq[LeagueInput] = {
    val str = scala.io.Source.fromFile("src/main/resources/leagues.yaml").getLines.mkString("\n")

    yaml.parser.parse(str)
      .leftMap(err => err: Error)
      .flatMap(_.as[List[LeagueInput]])
      .valueOr(throw _)
  }
}
