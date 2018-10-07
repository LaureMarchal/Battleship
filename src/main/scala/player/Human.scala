package player

import grid.Grid
import helpers.Helper._
import ship.{BoatType, Position, Ship}

/**
  * Human player
  * @param name string to differenciate the players on console
  * @param shipsGrid grid for ships of the player
  * @param shotsGrid grid for shots of the player
  * @param livePoints int to know when a player lost
  */
case class Human(name:String, var shipsGrid: Grid, var shotsGrid: Grid, var livePoints: Int) extends Player {

  override var ships: List[Ship] = Nil

  /**
    * Place the ships of a human player
    * @param shipsType list of boat type (name,size) to place
    * @return
    */
  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    if (shipsType.isEmpty)
      Nil
    else {
      val (direction,positions) = getPositionForShipPlacing(shipsType.head)
      if (!shipsGrid.isValidPlaceForShip(positions)) {
        placeShips(shipsType)
      } else {
        // create the ship
        val newShip = Ship(shipsType.head.name,shipsType.head.size,direction,positions)
        //place it on the grid
        shipsGrid = shipsGrid.placeOneShip(newShip,shipsGrid.grid)
        //add to player and continue placing ships
        newShip::placeShips(shipsType.tail)
      }
    }
  }

  override def chooseTarget() : Position = {
    getTargetFromInput()
  }
}
