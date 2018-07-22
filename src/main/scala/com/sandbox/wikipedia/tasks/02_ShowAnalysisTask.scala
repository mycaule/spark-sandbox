package com.sandbox
package wikipedia
package tasks

import org.slf4j.{ LoggerFactory, Logger }
import org.apache.spark.sql.functions._

import com.sandbox.models.{ LocalStorage, Context }
import models.LeagueStanding

case class ShowAnalysisTask(input: LocalStorage)(implicit context: Context) extends Runnable {
  import context.session.implicits._
  implicit private val logger: Logger = LoggerFactory.getLogger(getClass)

  override def run(): Unit = {
    val standings = context.session.read.parquet(input.path).as[LeagueStanding].cache()
    val teams = context.session.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("src/main/resources/teams.csv")

    println("Contrôle de la qualité des données")

    teams
      .orderBy("league", "team")
      .show()

    standings
      .filter(x => x.season == 2014 && x.league == "Bundesliga")
      .orderBy("position")
      .show()

    standings
      .describe("position", "points", "played", "goalsDifference")
      .show()

    println("Liste de toutes les équipes distinctes")
    standings.select("league", "team")
      .orderBy("league", "team")
      .distinct
      .show(false)
    //.write.csv("/tmp/wiki-data")

    println("Nombre moyen de buts par saison et par championnat")
    standings.createTempView("standingsSQL")
    context.session.sql("""
      SELECT league, season, round(mean(goalsFor),1) meanGoals
      FROM standingsSQL
      GROUP BY league, season
      ORDER BY league, season
    """)
      .show()

    println("Equipe la plus titrée de France")
    standings
      .filter(x => x.league == "Ligue 1" && x.position == 1)
      .groupBy("team", "position")
      .count
      .orderBy(desc("count"))
      .show(false)

    println("Nombre moyen de points des vainqueurs sur les 5 championnats")
    standings
      .filter(_.position == 1)
      .orderBy("league", "season")
      .groupBy("league", "position")
      .agg(mean("points"))
      .show()

    def decade(yyyy: Int) = (yyyy / 10) + "X"
    val dec = udf(decade _)

    println("Nombre moyen de points d'écart entre le 1er et le 10ème de chaque championnats et par décennie")
    standings
      .filter(x => x.position == 1 || x.position == 10)
      .withColumn("decade", dec(col("season")))
      .orderBy("league", "decade")
      .groupBy("league", "decade")
      .pivot("position")
      .agg(mean("points"))
      .groupBy("league", "decade")
      .agg(sum("1").alias("avgPoints1"), sum("10").alias("avgPoints10"))
      .withColumn("avgDelta1-10", col("avgPoints1") - col("avgPoints10"))
      .show()
  }
}
