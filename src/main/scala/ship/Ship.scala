package ship

import scala.collection.immutable.List

/**
  * Ship with name, size, direction, list of position
  * @param name to differentiate the ships
  * @param size to know when it's sunk
  * @param direction to help place it
  * @param positions list of (x,y) to know where on the grid
  */
case class Ship(name: String, size: Int, direction: String, positions: List[Position]) {

  /**
    * check if a ship has been sunk
    * @return true if size = 0 or else false
    */
  def isSunk(): Boolean = if (size == 0) true else false

  /**
    * update the positions attribute of ship by removing one position from the list when it is hit
    * @param target the position to remove
    * @return the ship updated
    */
  def setShotShip(target: Position): Ship = {
    val positionsToUpdate = positions
    val newPositions = positionsToUpdate.filter(x => x  != target)
    val newShip = Ship(name, size - 1, direction, newPositions)
    newShip
  }


}
