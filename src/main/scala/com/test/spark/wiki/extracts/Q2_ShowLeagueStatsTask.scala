package com.test
package spark.wiki.extracts

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import models.LeagueStanding

case class Q2_ShowLeagueStatsTask(bucket: String) extends Runnable {
  private val session: SparkSession = SparkSession.builder().getOrCreate()

  import session.implicits._

  override def run(): Unit = {
    val standings = session.read.parquet(bucket).as[LeagueStanding].cache()

    println("Contrôle rapide de la qualité des données")
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
      .distinct.show(500)

    println("Nombre moyen de buts par saison et par championnat")
    standings.createTempView("standingsSQL")
    session.sql("""
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
      .show()

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
