package com.test.faker

import org.scalatest._
import org.scalatest.prop.PropertyChecks

import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary

class FakerSpec extends PropSpec with PropertyChecks with Matchers {

  case class Location(a: String, b: Int)

  case class House(a: Int, b: Boolean, c: Option[Double], d: String, e: Option[Array[Int]], f: User)

  property("Faker generates random data for case classes") {
    println(Faker.generate[Location])
    println(Faker.generate[House])

    println(Faker.generate[List[Int]])
    println(Faker.generate[(Int, String, Short)])
    println(Faker.generate[Map[Float, Int]])

    println(arbitrary[Char])
    true shouldBe true
  }

  val integers = for (n <- Gen.choose(-1000, 1000)) yield 2 * n

  property("events integers") {
    forAll(integers) { n =>
      n % 2 shouldBe 0
    }
  }
}
