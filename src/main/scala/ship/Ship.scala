package ship

import scala.collection.immutable.List

case class Ship(name: String, size: Int, direction: String, positions: List[Position]) {

  /**
    * check if a ship has been sunk
    * @return
    */
  def isSunk(): Boolean = if (size == 0) true else false


}
