import ship.Ship
import ship.Position
import org.scalatest._

class ShipTest extends FlatSpec with Matchers {

  "isSunk" should "be true if size == 0" in {
    var sunk1 = Ship("sunk1", "N", List[Position]())
    var sunk2 = Ship("sunk2", "S", List[Position]())
    var sunk3 = Ship("sunk2", "E", List[Position]())
    var notSunk1 = Ship("notSunk1", "W", List[Position]())
    var notSunk2 = Ship("notSunk2", "S", List[Position]())
    var notSunk3 = Ship("notSunk3", "N", List[Position]())

    val newNotSunk1 = Ship(notSunk1.name,notSunk1.direction,Position(1, 0)::notSunk1.positions)

    var list2 = Position(5, 2)::notSunk2.positions
    list2 = Position(5, 3)::list2
    val newNotSunk2 = Ship(notSunk2.name,notSunk2.direction,Position(5, 4)::list2)

    var list3 = Position(6, 4)::notSunk3.positions
    list3 = Position(7, 4)::list3
    list3 = Position(8, 4)::list3
    val newNotSunk3 = Ship(notSunk3.name,notSunk3.direction,Position(9, 4)::list3)

    sunk1.isSunk() should be (true)
    sunk2.isSunk() should be (true)
    sunk3.isSunk() should be (true)

    newNotSunk1.isSunk() should be (false)
    newNotSunk2.isSunk() should be (false)
    newNotSunk3.isSunk() should be (false)
  }

  "size" should "be equal to positions.length" in {
    var shipEmpty = Ship("shipEmpty", "S", List[Position]())
    var ship1 = Ship("ship1", "W", List[Position]())
    var ship2 = Ship("ship2", "N", List[Position]())

    val newShip1 = Ship(ship1.name,ship1.direction,Position(5,2)::ship1.positions)

    val list2 = Position(8,6)::ship2.positions
    val newShip2 = Ship(ship2.name,ship2.direction,Position(0,0)::list2)

    shipEmpty.size() should be (0)
    newShip1.size() should be (1)
    newShip2.size() should be (2)
  }

  "setShotShip" should "remove the hit position from the list" in {
    var shipEmpty = Ship("shipEmpty", "S", List[Position]())

    var ship1 = Ship("ship1", "W", List[Position]())
    var ship1AtferShot = Ship("ship1", "W", List[Position]())

    var ship3 = Ship("ship3", "N", List[Position]())
    var ship3AfterShot = Ship("ship3", "N", List[Position]())
    var ship3After2Shot = Ship("ship3", "N", List[Position]())

    val newShip1 = Ship(ship1.name,ship1.direction,Position(5, 2)::ship1.positions)

    var list2 = Position(5, 2)::ship3.positions
    list2 = Position(5, 3)::list2
    val newShip3 = Ship(ship3.name,ship3.direction,Position(5, 4)::list2)

    var list3 = Position(5, 3)::ship3AfterShot.positions
    val newShip3AfterShot = Ship(ship3AfterShot.name,ship3AfterShot.direction,Position(5, 4)::list3)

    val newShip3After2Shot = Ship(ship3After2Shot.name,ship3After2Shot.direction,Position(5, 4)::ship3After2Shot.positions)

    val inListPos = Position(5, 2)
    val inListPos2 = Position(5, 3)
    val notInListPos = Position(1, 1)

    shipEmpty.setShotShip(notInListPos) should be (shipEmpty)
    newShip1.setShotShip(notInListPos) should be (newShip1)
    newShip3.setShotShip(notInListPos) should be (newShip3)

    newShip1.setShotShip(inListPos) should be (ship1AtferShot)
    newShip3.setShotShip(inListPos) should be (newShip3AfterShot)
    newShip3.setShotShip(inListPos).setShotShip(inListPos2) should be (newShip3After2Shot)
  }
}