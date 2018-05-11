
<p align="center">
  May 2018 SL Interview Assessment
</p>

<p align="center">
  <a href="http://travis-ci.org/mycaule/spark-wiki-extracts"><img src="https://api.travis-ci.org/mycaule/spark-wiki-extracts.svg?branch=master" alt="Build Status"></a>
  <br>
  <br>
</p>


### Utilisation
```
# Lancer les tests unitaires
sbt test
# Lancer le programme
sbt run
```

### Commentaires

#### Question 1

Voir [Spark Datasets Guide - Creating Datasets](https://people.apache.org/~pwendell/spark-nightly/spark-master-docs/latest/sql-programming-guide.html#creating-datasets)

#### Question 2

Voir [Use selector-syntax to find elements](https://jsoup.org/cookbook/extracting-data/selector-syntax).

Voir [How to add new functionality to closed classes](https://alvinalexander.com/scala/scala-for-loop-yield-examples-yield-tutorial#a-real-world-example).

Voir [Convert String to Int or None](https://stackoverflow.com/questions/23811425/scala-convert-string-to-int-or-none).

#### Question 3

Lorsque le job tourne sur un cluster [Elastic MapReduce](https://docs.aws.amazon.com/emr/latest/ReleaseGuide/emr-spark.html), les logs sont affichés sur [CloudWatch](https://aws.amazon.com/fr/cloudwatch/). Il suffit pour cela de rajouter un [appender dans Log4j](https://github.com/Virtual-Instruments/cloudwatch-log4j-appender).

#### Question 4

Voir [Managing Spark partitions with coalesce and repartition](https://hackernoon.com/managing-spark-partitions-with-coalesce-and-repartition-4050c57ad5c4)

#### Question 5

  [Parquet](https://parquet.apache.org) est un format d'échange standard pour l'écosystème Hadoop. Ses [avantages principaux](https://stackoverflow.com/questions/36822224/what-are-the-pros-and-cons-of-parquet-format-compared-to-other-formats) sont sa simplicité de représentation et le partitionnement en colonnes pour de grandes quantités de données.

#### Question 6

  Un `Dataset` est une structure de données permettant de paralleliser les données sur un cluster de machines. Outre les opérations classiques sur les `Seq`, on profite également de [moteurs d'optimisation](https://www.coursera.org/learn/scala-spark-big-data/lecture/yrfPh/datasets) de traitement inhérents à Spark.

### Spécifications

Créer un référentiel au format Apache Parquet contenant le classement général des 5 grands championnats de football européen sur les 40 dernières années en grapillant les informations sur wikipedia.

```html
<table class="wikitable gauche" style="text-align:center; line-height:16px;">
  <caption>Classement</caption>
  <tr bgcolor="#F2F2F2">
    <th scope="col" style="">Rang</th>
    <th scope="col" style="" width="200">Équipe</th>
    <th scope="col" style="width:20px"><span style="cursor:help;" title="Points">Pts</span></th>
    <th scope="col" style="width:20px"><span style="cursor:help;" title="Matchs joués">J</span></th>
    <th scope="col" style="width:20px;border-right-style:hidden"><span style="cursor:help;" title="Matchs gagnés">G</span></th>
    <th scope="col" style="width:20px;border-right-style:hidden"><span style="cursor:help;" title="Matchs nuls">N</span></th>
    <th scope="col" style="width:20px"><span style="cursor:help;" title="Matchs perdus">P</span></th>
    <th scope="col" style="width:20px;border-right-style:hidden"><span style="cursor:help;" title="Buts pour">Bp</span></th>
    <th scope="col" style="width:20px;border-right-style:hidden"><span style="cursor:help;" title="Buts contre">Bc</span></th>
    <th scope="col" style="width:25px"><span style="cursor:help;" title="Différence de buts">Diff</span></th>
  </tr>
  <tr bgcolor="white">
    <td><b><span class="nowrap">1</span></b></td>
    <td align="left"><span class="nowrap"><a href="/wiki/Lille_OSC" class="mw-redirect" title="Lille OSC">Lille</a></span></td>
    <td><b>44</b></td>
    <td>19</td>
    <td style="border-right-style:hidden">13</td>
    <td style="border-right-style:hidden">5</td>
    <td>1</td>
    <td style="border-right-style:hidden">40</td>
    <td style="border-right-style:hidden">17</td>
    <td>+23</td>
  </tr>
  ...
```

Il faudra extraire les données contenues dans les differents tableaux html et créer un export partquet ayant cette forme.

```text
+-------+------+--------+-------------+------+------+---+-----+----+--------+------------+---------------+
| league|season|position|         team|points|played|won|drawn|lost|goalsFor|goalsAgainst|goalsDifference|
+-------+------+--------+-------------+------+------+---+-----+----+--------+------------+---------------+
|Ligue 1|  2003|      10|         Nice|    55|    38| 13|   16|   9|      39|          31|              8|
|Ligue 1|  2008|      11|         Caen|    51|    38| 13|   12|  13|      48|          53|             -5|
|Ligue 1|  1979|       1|RC Strasbourg|    56|    38| 22|   12|   4|      68|          28|             40|
```  

##### Comment faire

Le test unitaire _com.test.spark.wiki.extracts.RunTasks_ permet de lancer les 2 tâches spark suivantes:

- com.test.spark.wiki.extracts.Q1_WikiDocumentsToParquetTask
  - La classe contient des petites questions générales
  - Cette classe genère les urls à crawler depuis le fichier _src/main/resources/leagues.yaml_
  - La classe ne compile pas
  - Il manque le parsing des pages wikipedia avec la librairie Jsoup
  - Il manque "l'activation" de spark
  - Générer des données en respectant le schéma de la classe _LeagueStanding_

- com.test.spark.wiki.extracts.Q2_ShowLeagueStatsTask
  - En utilisant les données générées par _Q1_WikiDocumentsToParquetTask_ répondre aux questions posées dans la classe

#### AWS
- Ecrire un fichier cloudformation yaml dans _cfn/s3.yml_ permettant de monter
  - Un bucket S3 "A"
  - Un bucket S3 "B" contenant les logs d'accès du bucket S3 "A"

- S'il fallait protéger les données sur un bucket S3 comment procéderiez-vous ?



Bonne session :)
