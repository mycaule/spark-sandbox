package com.sandbox
package faker

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
    forAll(integers)(n => n % 2 shouldBe 0)
  }

  val ints = Gen.choose(0, 100)

  property("sqrt") {
    forAll(ints)((d: Int) => math.sqrt(d.toDouble * d.toDouble) shouldEqual d)
  }

  property("list reverse") {
    forAll((lst: List[Int]) => lst.reverse.reverse shouldEqual lst)
  }

  trait Tree
  case class Node(left: Tree, right: Tree) extends Tree
  case class Leaf(x: Int) extends Tree

  def leafs: Gen[Leaf] = for {
    x <- ints
  } yield Leaf(x)

  def nodes: Gen[Node] = for {
    left <- trees
    right <- trees
  } yield Node(left, right)

  def trees: Gen[Tree] = Gen.oneOf(leafs, nodes)

  def options: Gen[Option[Int]] = Gen.oneOf(Some(scala.util.Random.nextInt), None)

  // property("options of int are positive") {
  //   forAll(options, maxDiscardedFactor(0.8))(x => x.getOrElse(1) should be >= 0)
  // }
}
