package player

import grid.Grid
import ship.{BoatType, Position, Ship}

/**
  * AI player
  * @param shipsGrid grid for ships of the player
  * @param shotsGrid grid for shots of the player
  * @param level level of difficulty of the AI
  * @param livePoints int to know when a player lost
  */
case class AI(var shipsGrid: Grid, var shotsGrid: Grid, level:Int, var livePoints: Int) extends Player {

  override val name: String = "Player AI"
  override var ships: List[Ship] = Nil

  /**
    * Place the ships of a AI player
    * @param shipsType list of boat type (name,size) to place
    * @return
    */
  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    if (shipsType.isEmpty)
      Nil
    else {
      val (direction,positions) = generateShipPlacingAI(shipsType.head,level)
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

  /**
    * Generate the place of a ship depending on AI level
    * @param shipType list of boat type (name,size) to place
    * @param level int (1,2,3)
    * @return
    */
  def generateShipPlacingAI(shipType: BoatType,level: Int) : (String,List[Position]) = {
    val direction = "S"
    val pos = Position(0,0)
    val positions = List.fill(shipType.size)(pos)
    (direction,positions)
  }
}
