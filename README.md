
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
```

### Commentaires

#### Question 1

Voir [Spark Datasets Guide - Creating Datasets](https://people.apache.org/~pwendell/spark-nightly/spark-master-docs/latest/sql-programming-guide.html#creating-datasets)

#### Question 2

Voir [Use selector-syntax to find elements](https://jsoup.org/cookbook/extracting-data/selector-syntax).

Voir [How to add new functionality to closed classes](https://alvinalexander.com/scala/scala-for-loop-yield-examples-yield-tutorial#a-real-world-example).

Voir [Convert String to Int or None](https://stackoverflow.com/questions/23811425/scala-convert-string-to-int-or-none).

On choisit 0 comme valeur numérique par défaut.

#### Question 3

Lorsque le job tourne sur un cluster [Elastic MapReduce](https://docs.aws.amazon.com/emr/latest/ReleaseGuide/emr-spark.html), les logs sont affichés sur [CloudWatch](https://aws.amazon.com/fr/cloudwatch/). Il suffit pour cela de rajouter un [appender dans Log4j](https://github.com/Virtual-Instruments/cloudwatch-log4j-appender).

#### Question 4

Voir [Managing Spark partitions with coalesce and repartition](https://hackernoon.com/managing-spark-partitions-with-coalesce-and-repartition-4050c57ad5c4)

#### Question 5

  [Parquet](https://parquet.apache.org) est un format d'échange standard pour l'écosystème Hadoop. Ses [avantages principaux](https://stackoverflow.com/questions/36822224/what-are-the-pros-and-cons-of-parquet-format-compared-to-other-formats) sont sa simplicité de représentation et le partitionnement en colonnes pour de grandes quantités de données.

#### Question 6

  Un `Dataset` est une structure de données permettant de paralleliser les données sur un cluster de machines. Outre les opérations classiques sur les `Seq`, on profite également de [moteurs d'optimisation](https://www.coursera.org/learn/scala-spark-big-data/lecture/yrfPh/datasets) de traitement inhérents à Spark.

#### Question 7

Voir [Intro to the Jackson ObjectMapper](http://www.baeldung.com/jackson-object-mapper-tutorial).

#### Question 8

Voir [Jackson annotation examples](http://www.baeldung.com/jackson-annotations).

#### Question AWS-1

Cloud Formation permet de créer des ressources sur AWS par [configuration d'un fichier](https://github.com/awslabs/aws-cloudformation-templates/tree/master/aws/services/S3) (Infrastructure as Code).

Voir [AWS CloudFormation / AWS::S3::Bucket](https://docs.aws.amazon.com/fr_fr/AWSCloudFormation/latest/UserGuide/aws-properties-s3-bucket.html) pour savoir comment définir des ressources S3.

Voir [Server Access Logging](https://docs.aws.amazon.com/AmazonS3/latest/dev/ServerLogs.html) pour les logs d'accès.

#### Question AWS-2

Pour [protéger les données](https://aws.amazon.com/premiumsupport/knowledge-center/secure-s3-resources/https://aws.amazon.com/premiumsupport/knowledge-center/secure-s3-resources/) sur un bucket S3, il faut mettre en place des rôles et des groupes d'utilisateurs, des credentials permettent à ces groupes de se connecter.

Pour se prémunir des fuites de données on veillera à auditer ces données et à utiliser les techniques cryptographiques courantes (SSL au niveau de la connexion, GPG au niveau des données, etc.).

### Spécifications

Export Parquet.
```text
+-------+------+--------+-------------+------+------+---+-----+----+--------+------------+---------------+
| league|season|position|         team|points|played|won|drawn|lost|goalsFor|goalsAgainst|goalsDifference|
+-------+------+--------+-------------+------+------+---+-----+----+--------+------------+---------------+
|Ligue 1|  2003|      10|         Nice|    55|    38| 13|   16|   9|      39|          31|              8|
|Ligue 1|  2008|      11|         Caen|    51|    38| 13|   12|  13|      48|          53|             -5|
|Ligue 1|  1979|       1|RC Strasbourg|    56|    38| 22|   12|   4|      68|          28|             40|
```  

##### TODO

- [ ] En utilisant les données générées par _Q1_WikiDocumentsToParquetTask_ répondre aux questions posées dans la classe Q2_ShowLeagueStatsTask
- [ ] [Valider](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-validate-template.html) le template Cloud Formation
- [ ] Nettoyer les noms des équipes
