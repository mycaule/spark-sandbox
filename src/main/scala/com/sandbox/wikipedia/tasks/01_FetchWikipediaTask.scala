package com.sandbox
package wikipedia
package tasks

import org.slf4j.{ LoggerFactory, Logger }
import org.apache.spark.sql.SaveMode
import com.sandbox.models.{ LocalStorage, Context }
import models.{ LeagueInput, LeagueStanding }

case class FetchWikipediaTask(output: LocalStorage)(implicit context: Context) extends Runnable {
  import context.session.implicits._
  implicit private val logger: Logger = LoggerFactory.getLogger(getClass)

  override def run(): Unit = {
    val toYear = 2017
    val fromYear = toYear - 40

    LeagueInput.getLeagues().toDS()
      .flatMap(input => (fromYear until toYear)
        .map(year => (year + 1, input.name, input.url.format(year, year + 1)))
      )
      .flatMap((LeagueStanding.fetch _).tupled)
      .repartition(2)
      .write
      .mode(SaveMode.Overwrite)
      .parquet(output.path)
  }
}
