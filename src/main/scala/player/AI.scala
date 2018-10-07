package player

import scala.util.Random

import grid.Grid
import ship.{BoatType, Position, Ship}
import helpers.Helper._

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

  override def chooseTarget() : Position = {
    getRandomTarget()
  }

  /**
    * Generate the place of a ship depending on AI level
    * @param shipType list of boat type (name,size) to place
    * @param level int (1,2,3)
    * @return
    */
  def generateShipPlacingAI(shipType: BoatType,level: Int) : (String,List[Position]) = {
    val random = new Random()
    var direction = ""
    var posFirst = Position(0,0)
    val dirRandom = random.nextInt(4)
    val posRandom = random.nextInt(10)
    dirRandom match {
      case 0 =>
        direction = "N"
        posFirst = Position(posRandom,9)
      case 1 =>
        direction = "S"
        posFirst = Position(posRandom,0)
      case 2 =>
        direction = "E"
        posFirst = Position(0,posRandom)
      case 3 =>
        direction = "W"
        posFirst = Position(9,posRandom)
      case _ => generateShipPlacingAI(shipType,level)
    }
    val positions = List.fill(shipType.size)(posFirst)
    (direction,positions)
  }

}
