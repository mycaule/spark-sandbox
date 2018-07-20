package com.test.faker

import shapeless.{ HList, HNil, _ }
import scala.util.Random

object RandomUtils {
  val loremWords = List("lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
    "adipisicing", "elit", "sed", "do", "eiusmod", "tempor", "incididunt", "ut",
    "labore", "et", "dolore", "magna", "aliqua", "ut", "enim", "ad", "minim",
    "veniam", "quis", "nostrud", "exercitation", "ullamco", "laboris", "nisi",
    "ut", "aliquip", "ex", "ea", "commodo", "consequat", "duis", "aute", "irure",
    "dolor", "in", "reprehenderit", "in", "voluptate", "velit", "esse", "cillum",
    "dolore", "eu", "fugiat", "nulla", "pariatur", "excepteur", "sint", "occaecat",
    "cupidatat", "non", "proident", "sunt", "in", "culpa", "qui", "officia",
    "deserunt", "mollit", "anim", "id", "est", "laborum")
}

// https://dzone.com/articles/generic-programming-in-scala-with-shapeless
trait Faker[A] {
  def generate: A
}

object Faker {
  val MAP_SIZE = 3
  val SEQ_SIZE = 10

  def generate[A](implicit gen: Faker[A]) = gen.generate

  // Basic types generators
  implicit def shortGen = new Faker[Short] {
    override def generate = Random.nextInt.toShort
  }

  implicit def byteGen = new Faker[Byte] {
    override def generate = Random.nextInt.toByte
  }

  implicit def intGen = new Faker[Int] {
    override def generate = Random.nextInt
  }

  implicit def longGen = new Faker[Long] {
    override def generate = Random.nextLong
  }

  implicit def doubleGen = new Faker[Double] {
    override def generate = Random.nextDouble
  }

  implicit def floatGen = new Faker[Float] {
    override def generate = Random.nextFloat
  }

  implicit def stringGen = new Faker[String] {
    override def generate = Random.shuffle(RandomUtils.loremWords).take(5).mkString(" ")
  }

  implicit def booleanGen = new Faker[Boolean] {
    override def generate = Random.nextBoolean
  }

  // Nested types generators
  implicit def optGen[A](implicit gen: Faker[A]) = new Faker[Option[A]] {
    override def generate = Some(gen.generate)
  }

  implicit def seqGen[A](implicit gen: Faker[A]) = new Faker[Seq[A]] {
    override def generate = Seq.fill(SEQ_SIZE)(gen.generate)
  }

  implicit def lstGen[A](implicit gen: Faker[A]) = new Faker[List[A]] {
    override def generate = List.fill(SEQ_SIZE)(gen.generate)
  }

  implicit def arrGen[A: scala.reflect.ClassTag](implicit gen: Faker[A]) = new Faker[Array[A]] {
    override def generate = Array.fill[A](SEQ_SIZE)(gen.generate)
  }

  implicit def mapGen[A, B](implicit genA: Faker[A], genB: Faker[B]) = new Faker[Map[A, B]] {
    override def generate = {
      (List.fill(MAP_SIZE)(genA.generate) zip List.fill(MAP_SIZE)(genB.generate)).toMap
    }
  }

  // HList generator
  implicit def hnilGen = new Faker[HNil] {
    override def generate = HNil
  }

  implicit def hconsGen[H, T <: HList](implicit headGen: Faker[H], tailGen: Faker[T]) =
    new Faker[H :: T] {
      override def generate = headGen.generate :: tailGen.generate
    }

  // case class generator
  implicit def genericToGen[T, L <: HList](implicit generic: Generic.Aux[T, L], lGen: Faker[L]): Faker[T] =
    new Faker[T] {
      override def generate = generic.from(lGen.generate)
    }

  // Custom types
  implicit def userGen = new Faker[User] {
    override def generate = Faker.generate[User]
  }
}

case class User(firstname: String, lastname: String)
