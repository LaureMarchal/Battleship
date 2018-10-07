import ship.Position
import org.scalatest._

class PositionTest extends FlatSpec with Matchers {

  "isValidPosition" should "be true if the position is in a 10x10 grid" in {
    val posValid1 = Position(0, 0)
    val posValid2 = Position(9, 9)
    val posValid3 = Position(3, 8)
    val posInvalid1 = Position(-1, -1)
    val posInvalid2 = Position(11, 11)
    val posInvalid3 = Position(4, -2)
    val posInvalid4 = Position(-15, 5)
    val posInvalid5 = Position(2, 100)
    val posInvalid6 = Position(23, 9)

    posValid1.isValidPosition() should be (true)
    posValid2.isValidPosition() should be (true)
    posValid3.isValidPosition() should be (true)

    posInvalid1.isValidPosition() should be (false)
    posInvalid2.isValidPosition() should be (false)
    posInvalid3.isValidPosition() should be (false)
    posInvalid4.isValidPosition() should be (false)
    posInvalid5.isValidPosition() should be (false)
    posInvalid6.isValidPosition() should be (false)
  }
}