
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
# Compiler le projet
sbt compile
# Lancer les tests unitaires
sbt test
```

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

La commande suivante permet de valider le fichier (nécessite un compte actif):

```
aws cloudformation validate-template --template-body file:///home/michel/Code/spark-wiki-extracts/cfn/s3.yml
```

#### Question AWS-2

Pour [protéger les données](https://aws.amazon.com/premiumsupport/knowledge-center/secure-s3-resources/https://aws.amazon.com/premiumsupport/knowledge-center/secure-s3-resources/) sur un bucket S3, il faut mettre en place des rôles et des groupes d'utilisateurs, ces groupes se connectent par le biais d'identifiants secrets.

Pour se prémunir des fuites de données on veillera à auditer ces données et à utiliser les techniques cryptographiques courantes (SSL au niveau de la connexion, GPG au niveau des données, etc.).

#### Faits remarquables

Ces faits sont sujets à interprétation et peuvent donner des résultats alternatifs aux questions posées.

- [France 1992-1993](https://fr.wikipedia.org/wiki/Championnat_de_France_de_football_1992-1993) : Marseille est déclassé suite à une affaire de corruption
- [Angleterre 1996-1997](https://fr.wikipedia.org/wiki/Championnat_d'Angleterre_de_football_1996-1996) : Middlesbrough se voit retirer 3 points
- [Italie 2004-2005](https://fr.wikipedia.org/wiki/Championnat_d'Italie_de_football_2004-2005) : La Juventus s'est vu retirée son titre à la suite du scandale des matchs truqués
- [France 2012-2013](https://fr.wikipedia.org/wiki/Championnat_de_France_de_football_2012-2013) : Ajaccio commence la saison avec 2 points de pénalités

#### Remarques techniques

L'utilisation de [Scala Scraper](https://github.com/ruippeixotog/scala-scraper) aurait été préférable à JSoup. Cela évite d'avoir à utiliser les types Java et permet de privilégier le type `Option` plutôt qu'`Exception`.

Idem pour [Circe](https://github.com/circe/circe) plutôt que Jackson DataFormat.

Le problème des variantes de nom d'équipe peut être résolu à l'aide d'une recherche de [synonymes avec l'algorithme Word2Vec](https://www.quora.com/What-are-good-ways-to-automatically-find-synonyms-using-machine-learning-ML-techniques-What-are-good-ways-to-automatically-find-antonyms-using-ML-techniques) [DIMSUM](https://databricks.com/blog/2014/10/20/efficient-similarity-algorithm-now-in-spark-twitter.html) et de la [distance de Levenshtein](https://medium.com/@mrpowers/fuzzy-matching-in-spark-with-soundex-and-levenshtein-distance-6749f5af8f28). L'implémentation peut être [faite dans Spark](https://spark.apache.org/docs/2.2.0/mllib-feature-extraction.html#word2vec) mais cela dépasse le cadre de l'exercice.

Nous nous sommes ici contentés de [nettoyer de manière grossière](src/main/scala/com/test/models/LeagueStanding.scala) les chaînes de caractères et de construire une liste d'équipe de référence dans [teams.csv](src/main/resources/teams.csv) qui permet après consolidation, les doublons pouvant être éliminés manuellement, de reconstruire un nom d'équipe "standard".

Le problème peut toutefois être contourné en exploitant la régularité de l'URL wikipedia propre à chaque équipe. Les liens sont en effet toujours bien renseignés dans notre cas. Par ailleurs, Wikipedia fournit une API qui permet de récupérer les différents alias d'une page.
