package ship

import scala.collection.immutable.List

case class Ship(name: String, size: Int, direction: String, positions: List[Position]) {

  /**
    * check if a ship has been sunk
    * @return
    */
  def isSunk(): Boolean = if (size == 0) true else false

  def setShotShip(target: Position): Ship = {
    val positionsToUpdate = positions
    val newPositions = positionsToUpdate.filter(x => x  != target)
    val newShip = Ship(name, size - 1, direction, newPositions)
    newShip
  }


}
