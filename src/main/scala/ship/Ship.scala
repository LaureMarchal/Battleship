package ship

import scala.collection.immutable.List

/**
  * Ship with name,direction, list of position
  * @param name to differentiate the ships
  * @param direction to help place it
  * @param positions list of (x,y) to know where on the grid
  */
case class Ship(name: String, direction: String, positions: List[Position]) {

  /**
    * check if a ship has been sunk
    * @return true if no more positions or else false
    */
  def isSunk(): Boolean = positions.isEmpty

  /**
    * get the size of a ship by the left positions
    * @return int size
    */
  def size(): Int = positions.length

  /**
    * update the positions attribute of ship by removing one position from the list when it is hit
    * @param target the position to remove
    * @return the ship updated
    */
  def setShotShip(target: Position): Ship = {
    val positionsToUpdate = positions
    val newPositions = positionsToUpdate.filter(x => x  != target)
    val newShip = Ship(name, direction, newPositions)
    newShip
  }


}
