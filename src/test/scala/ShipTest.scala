import org.scalatest._

class ShipTest extends FunSuite with DiagrammedAssertions{
  test("Battleship should start with B") {
    assert("Battleship".startsWith("B"))
  }
}
