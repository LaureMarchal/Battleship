import ship.Ship
import ship.Position
import org.scalatest._

class ShipTest extends FlatSpec with Matchers {

  "isSunk" should "be true if size == 0" in {
    var sunk1 = Ship("sunk1", 0, "N", List[Position]())
    var sunk2 = Ship("sunk2", 0, "S", List[Position]())
    var sunk3 = Ship("sunk2", 0, "E", List[Position]())
    var sunk4 = Ship("sunk2", 0, "W", List[Position]())
    var notSunk1 = Ship("notSunk1", 1, "W", List[Position]())
    var notSunk2 = Ship("notSunk2", 3, "S", List[Position]())
    var notSunk3 = Ship("notSunk3", 4,  "N", List[Position]())

    Position(1, 0)::notSunk1.positions

    Position(5, 2)::notSunk2.positions
    Position(5, 3)::notSunk2.positions
    Position(5, 4)::notSunk2.positions

    Position(6, 4)::notSunk3.positions
    Position(7, 4)::notSunk3.positions
    Position(8, 4)::notSunk3.positions
    Position(9, 4)::notSunk3.positions

    sunk1.isSunk() should be (true)
    sunk2.isSunk() should be (true)
    sunk3.isSunk() should be (true)

    notSunk1.isSunk() should be (false)
    notSunk2.isSunk() should be (false)
    notSunk3.isSunk() should be (false)
  }

  "setShotShip" should "remove the hit position from the list" in {
    var shipEmpty = Ship("shipEmpty", 0, "S", List[Position]())

    var ship1 = Ship("ship1", 1, "W", List[Position]())
    var ship1AtferShot = Ship("ship1", 0, "W", List[Position]())

    var ship3 = Ship("ship3", 3, "N", List[Position]())
    var ship3AfterShot = Ship("ship3", 2, "N", List[Position]())
    var ship3After2Shot = Ship("ship3", 1, "N", List[Position]())

    Position(5, 2)::ship1.positions

    Position(5, 2)::ship3.positions

    Position(5, 3)::ship3.positions
    Position(5, 3)::ship3AfterShot.positions

    Position(5, 4)::ship3.positions
    Position(5, 4)::ship3AfterShot.positions
    Position(5, 4)::ship3After2Shot.positions

    val inListPos = Position(5, 2)
    val inListPos2 = Position(5, 3)
    val notInListPos = Position(1, 1)

    shipEmpty.setShotShip(notInListPos) should be (shipEmpty)
    ship1.setShotShip(notInListPos) should be (ship1)
    ship3.setShotShip(notInListPos) should be (ship3)

    ship1.setShotShip(inListPos) should be (ship1AtferShot)
    ship3.setShotShip(inListPos) should be (ship3AfterShot)
    ship3.setShotShip(inListPos).setShotShip(inListPos2) should be (ship3After2Shot)
  }
}