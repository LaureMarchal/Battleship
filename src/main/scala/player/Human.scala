package player

import grid.Grid
import helpers.BoatType
import helpers.Utils._
import ship.Ship

case class Human(name:String, var shipsGrid: Grid, var shotsGrid: Grid, var livePoints: Int) extends Player {

  override var ships: List[Ship] = Nil

  /**
    *
    * @param shipsType
    * @return
    */
  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    if (shipsType.isEmpty)
      Nil
    else {
      val newShip = getPositionForShipPlacing(shipsType.head)
      if (!shipsGrid.isValidPlaceForShip(newShip)) {
        placeShips(shipsType)
      } else {
        shipsGrid = shipsGrid.placeOneShip(newShip,shipsGrid.grid)
        newShip::placeShips(shipsType.tail)
      }
    }
  }
}
