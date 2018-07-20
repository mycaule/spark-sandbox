package com.test.faker

import org.scalatest._
import org.scalatest.Matchers._

class FakerSpec extends FlatSpec with Matchers {

  case class Location(a: String, b: Int)

  case class House(a: Int, b: Boolean, c: Option[Double], d: String, e: Option[Array[Int]], f: User)

  "The Faker object" should "generate random data for case classes" in {
    println(Faker.generate[Location])
    println(Faker.generate[House])

    println(Faker.generate[List[Int]])
    println(Faker.generate[(Int, String, Short)])
    println(Faker.generate[Map[Float, Int]])

    true shouldBe true
  }
}
