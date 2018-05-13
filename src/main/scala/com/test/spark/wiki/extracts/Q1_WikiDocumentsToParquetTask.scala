package com.test
package spark.wiki.extracts

import org.slf4j.LoggerFactory
import org.apache.spark.sql.{ SaveMode, SparkSession }
import models.{ LeagueInput, LeagueStanding }

case class Q1_WikiDocumentsToParquetTask(bucket: String) extends Runnable {
  private val session: SparkSession = SparkSession.builder()
    .appName("Wiki Documents")
    .getOrCreate()

  implicit private val logger = LoggerFactory.getLogger(getClass)

  import session.implicits._

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
      .parquet(bucket)
  }
}
