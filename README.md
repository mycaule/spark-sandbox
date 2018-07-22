Spark Sandbox [![travis-badge][]][travis]
=======

[travis]:                https://travis-ci.org/mycaule/spark-sandbox
[travis-badge]:          https://travis-ci.org/mycaule/spark-sandbox.svg?branch=master

This repository is for tests and learning the Spark and Scalatest frameworks.

### Usage

Maven
```
# MAVEN_OPTS="-Xss2048K"
mvn compile
mvn test
```

sbt
```
# SBT_OPTS="-Xss2048K"
sbt compile
sbt test
```

Running the tasks
```
sbt:spark-sandbox> test:compile
sbt:spark-sandbox> testOnly com.sandbox.runner.RunTasks
# or
scala> (new RunTasks).execute()
```

### Roadmap

- [x] Full support of Maven and sbt build tools
- [ ] Move tasks to specific folder and run them with [scalatest runner](http://www.scalatest.org/user_guide/using_the_runner) (command line script? as integrations tests?)
- [ ] Provide better scalatest report
- [ ] Low level scheduling with [Quartz Scheduler](https://github.com/enragedginger/akka-quartz-scheduler)
- [ ] Convert to a [giter8](https://github.com/foundweekends/giter8) template

- Use [Circe](https://github.com/circe/circe) instead of Jackson DataFormat.
- Search synonyms with [Word2Vec](https://www.quora.com/What-are-good-ways-to-automatically-find-synonyms-using-machine-learning-ML-techniques-What-are-good-ways-to-automatically-find-antonyms-using-ML-techniques), [DIMSUM](https://databricks.com/blog/2014/10/20/efficient-similarity-algorithm-now-in-spark-twitter.html), or [Levenshtein distance](https://medium.com/@mrpowers/fuzzy-matching-in-spark-with-soundex-and-levenshtein-distance-6749f5af8f28) with [Spark ML](https://spark.apache.org/docs/2.2.0/mllib-feature-extraction.html#word2vec)
- [Clean names algorithm](src/main/scala/com/test/models/LeagueStanding.scala) and make a list of teams: [teams.csv](src/main/resources/teams.csv)
- Check Wikipedia API for page aliases (URL unicity)


### Comments

On choisit une valeur pénalisante comme valeur numérique par défaut pour refaire sortir l'anomalie au lieu d'une exception.

Lors du scrapping HTML, on utilise l'ordre des colonnes pour reconstituer les paramètres de chaque équipe.

On peut recalculer les colonnes *Points* et *Différence de buts* pour éviter une erreur de parsing.

Une inspection des données scrapées en première approche montre que cet ordre a en général été respecté sauf pour les pages suivantes : [Italie 2004-2005](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2004-2005), [Italie 2005-2006](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2005-2006), [Italie 2006-2007](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2006-2007), [Angleterre 1977-1978](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1977-1978), [Angleterre 1979-1980](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1979-1980), [Angleterre 1987-1988](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1987-1988)

Ces faits sont sujets à interprétation et peuvent donner des résultats alternatifs aux questions posées.

- [France 1992-1993](https://fr.wikipedia.org/wiki/Championnat_de_France_de_football_1992-1993) : Marseille est déclassé suite à une affaire de corruption
- [Angleterre 1996-1997](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1996-1996) : Middlesbrough se voit retirer 3 points
- [Italie 2004-2005](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2004-2005) : La Juventus s'est vu retirée son titre à la suite du scandale des matchs truqués
- [France 2012-2013](https://fr.wikipedia.org/wiki/Championnat_de_France_de_football_2012-2013) : Ajaccio commence la saison avec 2 points de pénalités

### References

- [Spark Datasets Guide - Creating Datasets](https://people.apache.org/~pwendell/spark-nightly/spark-master-docs/latest/sql-programming-guide.html#creating-datasets)
- [How to add new functionality to closed classes](https://alvinalexander.com/scala/scala-for-loop-yield-examples-yield-tutorial#a-real-world-example)
- [Convert String to Int or None](https://stackoverflow.com/questions/23811425/scala-convert-string-to-int-or-none)
- [Managing Spark partitions with coalesce and repartition](https://hackernoon.com/managing-spark-partitions-with-coalesce-and-repartition-4050c57ad5c4)
- [Stackoverflow - Pros and cons of Parquet format](https://stackoverflow.com/questions/36822224/what-are-the-pros-and-cons-of-parquet-format-compared-to-other-formats)
- [Coursera - Spark Datasets](https://www.coursera.org/learn/scala-spark-big-data/lecture/yrfPh/datasets)
- [Intro to the Jackson ObjectMapper](http://www.baeldung.com/jackson-object-mapper-tutorial)
- [Jackson annotation examples](http://www.baeldung.com/jackson-annotations)
