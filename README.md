
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

### TODO List

- [ ] Finir de répondre aux questions sur le DataSet construit
- [ ] [Valider](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/using-cfn-validate-template.html) le template Cloud Formation
- [ ] Proposer un ordre alternatif par configuration pour les 6 saisons problématiques du dessus

### Détails d'implémentation

#### Question 1

Voir [Spark Datasets Guide - Creating Datasets](https://people.apache.org/~pwendell/spark-nightly/spark-master-docs/latest/sql-programming-guide.html#creating-datasets)

#### Question 2

Voir [Use selector-syntax to find elements](https://jsoup.org/cookbook/extracting-data/selector-syntax).

Voir [How to add new functionality to closed classes](https://alvinalexander.com/scala/scala-for-loop-yield-examples-yield-tutorial#a-real-world-example).

Voir [Convert String to Int or None](https://stackoverflow.com/questions/23811425/scala-convert-string-to-int-or-none).

On choisit une valeur pénalisante comme valeur numérique par défaut pour refaire sortir l'anomalie au lieu d'une exception.

Lors du scrapping HTML, on utilise l'ordre des colonnes pour reconstituer les paramètres de chaque équipe.

On peut recalculer les colonnes *Points* et *Différence de buts* pour éviter une erreur de parsing.

Une inspection des données scrapées en première approche montre que cet ordre a en général été respecté sauf pour les pages suivantes : [Italie 2004-2005](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2004-2005), [Italie 2005-2006](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2005-2006), [Italie 2006-2007](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2006-2007), [Angleterre 1977-1978](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1977-1978), [Angleterre 1979-1980](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1979-1980), [Angleterre 1987-1988](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1987-1988)

Pour être plus robuste, il faut en toute rigueur maintenir un dictionnaire des synonymes pour chaque nom de colonne.

#### Question 3

Lorsque le job tourne sur un cluster [Elastic MapReduce](https://docs.aws.amazon.com/emr/latest/ReleaseGuide/emr-spark.html), les logs sont affichés sur [CloudWatch](https://aws.amazon.com/fr/cloudwatch/). Il suffit pour cela de rajouter un [appender dans Log4j](https://github.com/Virtual-Instruments/cloudwatch-log4j-appender).

#### Question 4

Voir [Managing Spark partitions with coalesce and repartition](https://hackernoon.com/managing-spark-partitions-with-coalesce-and-repartition-4050c57ad5c4).

#### Question 5

  [Parquet](https://parquet.apache.org) est un format d'échange standard pour l'écosystème Hadoop. Ses [avantages principaux](https://stackoverflow.com/questions/36822224/what-are-the-pros-and-cons-of-parquet-format-compared-to-other-formats) sont sa simplicité de représentation et le partitionnement en colonnes pour de grandes quantités de données.

#### Question 6

  Un `Dataset` est une structure de données permettant de paralléliser les données sur un cluster de machines. Outre les opérations classiques sur les `Seq`, on profite également de [moteurs d'optimisation](https://www.coursera.org/learn/scala-spark-big-data/lecture/yrfPh/datasets) de traitement inhérents à Spark.

#### Question 7

Voir [Intro to the Jackson ObjectMapper](http://www.baeldung.com/jackson-object-mapper-tutorial).

#### Question 8

Voir [Jackson annotation examples](http://www.baeldung.com/jackson-annotations).

#### Question AWS-1

Cloud Formation permet de créer des ressources sur AWS par [configuration d'un fichier](https://github.com/awslabs/aws-cloudformation-templates/tree/master/aws/services/S3) (Infrastructure as Code).

Voir [AWS CloudFormation / AWS::S3::Bucket](https://docs.aws.amazon.com/fr_fr/AWSCloudFormation/latest/UserGuide/aws-properties-s3-bucket.html) pour savoir comment définir des ressources S3.

Voir [Server Access Logging](https://docs.aws.amazon.com/AmazonS3/latest/dev/ServerLogs.html) pour les logs d'accès.

#### Question AWS-2

Pour [protéger les données](https://aws.amazon.com/premiumsupport/knowledge-center/secure-s3-resources/https://aws.amazon.com/premiumsupport/knowledge-center/secure-s3-resources/) sur un bucket S3, il faut mettre en place des rôles et des groupes d'utilisateurs, ces groupes se connectent par le biais d'identifiants secrets.

Pour se prémunir des fuites de données on veillera à auditer ces données et à utiliser les techniques cryptographiques courantes (SSL au niveau de la connexion, GPG au niveau des données, etc.).

#### Faits remarquables

Ces faits sont sujets à interprétation et peuvent donner des résultats alternatifs aux questions posées.
- [France 2012-2013](https://fr.wikipedia.org/wiki/Championnat_de_France_de_football_2012-2013) : Ajaccio commence la saison avec 2 points de pénalités
- [Italie 2004-2005](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2004-2005) : le champion s'est vu retiré son titre à la suite du scandale des matchs truqués
