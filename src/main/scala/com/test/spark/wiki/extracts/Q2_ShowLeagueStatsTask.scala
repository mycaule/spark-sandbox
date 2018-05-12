package com.test.spark.wiki.extracts

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

case class Q2_ShowLeagueStatsTask(bucket: String) extends Runnable {
  private val session: SparkSession = SparkSession.builder().getOrCreate()

  import session.implicits._

  override def run(): Unit = {
    val standings = session.read.parquet(bucket).as[LeagueStanding].cache()

    println("Contrôle rapide de la qualité des données")
    standings
      .filter(x => x.season == 2014 && x.league == "Bundesliga")
      .show()

    println("Par nombre de points croissants")
    standings
      .orderBy("points")
      .show()

    println("Par nombre de points décroissants")
    standings
      .orderBy(desc("points"))
      .show()

    standings
      .orderBy("played")
      .show()

    standings
      .orderBy(desc("goalsDifference"))
      .show()

    println("Liste de toutes les équipes distinctes")
    standings.select("league", "team")
      .orderBy("team")
      // .filter(col("team").startsWith("B"))
      .distinct.show(500)

    println("Nombre moyen de buts par saison et par championnat")
    standings.createTempView("standingsSQL")
    session.sql("""
      SELECT season, league, round(mean(goalsFor),1) mean_goals
      FROM standingsSQL
      GROUP BY season, league
      ORDER BY season, league
    """)
      .show()

    // TODO Q2
    println("En Dataset, quelle est l'équipe la plus titrée de France ?")

    // TODO Q3
    println("En Dataset, quelle est la moyenne de points des vainqueurs sur les 5 différents championnats ?")

    // TODO Q5 Ecrire une udf spark "decade" qui retourne la décennie d'une saison sous la forme 199X ?

    // TODO Q4
    println("En Dataset, quelle est la moyenne de points d'écart entre le 1er et le 10ème de chaque championnats " +
      "par décennie")

  }
}
