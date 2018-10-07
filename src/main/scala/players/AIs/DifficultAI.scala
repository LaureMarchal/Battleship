package players.AIs

import grid.CaseType.CaseType
import grid.{CaseType, Grid}
import helpers.Helper.{getRandomDirectionStart, getRandomTarget}
import players.Player
import ship.{BoatType, Position, Ship}

import scala.annotation.tailrec

case class DifficultAI(var shipsGrid: Grid, var shotsGrid: Grid, var livePoints: Int) extends Player {

  override val name: String = "Difficult AI"
  override var ships: List[Ship] = Nil
  var lastShots:List[Position] = Nil
  var shipPosFirst:Position = Position(0,0)

  /**
    * Generate the place of a ship depending on AI level
    * @param shipType list of boat type (name,size) to place
    * @return
    */
  def generateShipPlacingAI(shipType: BoatType,shipPosFirst:Position) : (String,List[Position]) = {
    val (direction,posStart) = getRandomDirectionStart(shipPosFirst)
    @tailrec
    def generateRandom(direction: String,posStart:Position,size: Int,list:List[Position]) : (String,List[Position]) = {
      if (list.length == size)
        (direction,list)
      else {
        direction match {
          case "N" =>
            val newPos = Position(posStart.x,posStart.y - 1)
            generateRandom(direction,newPos,size,newPos::list)
          case "S" =>
            val newPos = Position(posStart.x,posStart.y + 1)
            generateRandom(direction,newPos,size,newPos::list)
          case "E" =>
            val newPos = Position(posStart.x + 1,posStart.y)
            generateRandom(direction,newPos,size,newPos::list)
          case "W" =>
            val newPos = Position(posStart.x - 1,posStart.y)
            generateRandom(direction,newPos,size,newPos::list)
        }
      }
    }
    generateRandom(direction,posStart,shipType.size,Nil)
  }

  /**
    * Place the ships of a AI player
    * @param shipsType list of boat type (name,size) to place
    * @return
    */
  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    if (shipsType.isEmpty)
      Nil
    else {
      val (direction,positions) = generateShipPlacingAI(shipsType.head,shipPosFirst)
      if (!shipsGrid.isValidPlaceForShip(positions)) {
        placeShips(shipsType)
      } else {
        // create the ship
        shipPosFirst = Position(shipPosFirst.x + 1,shipPosFirst.y + 1)
        val newShip = Ship(shipsType.head.name,shipsType.head.size,direction,positions)
        //place it on the grid
        shipsGrid = shipsGrid.placeOneShip(newShip,shipsGrid.grid)
        //add to player and continue placing ships
        newShip::placeShips(shipsType.tail)
      }
    }
  }

  /**
    * choose or get the target position
    * @return
    */
  override def chooseTarget() : Position = {
    getRandomTarget()
  }

  /**
    * Shoot a target
    * @param target position to shoot
    * @param opponent the player who is attacked
    * @return the CaseType : result of the shot
    */
  override def shoot(target: Position,opponent:Player): CaseType = {
    val opponentGrid = opponent.shipsGrid.grid
    val caseAttacked = opponentGrid(target.x)(target.y)
    caseAttacked match {
      case CaseType.S =>
        val hit = CaseType.H
        // update player shotsgrid
        shotsGrid = shotsGrid.setCase(shotsGrid.grid, target.x, target.y, hit)
        // update opponent ships grid
        opponent.shipsGrid = opponent.shipsGrid.setCase(opponentGrid, target.x, target.y, hit)
        // update opponent ships and check if the ship is sunk
        val isSunkShip = opponent.updateShips(target)
        // update opponent live points
        opponent.livePoints -= 1
        //return the result
        if (isSunkShip)
          CaseType.Sunk
        else
          hit
      case CaseType.W =>
        val missed = CaseType.M
        // update player shotsgrid
        shotsGrid = shotsGrid.setCase(shotsGrid.grid, target.x, target.y, missed)
        // update opponent ships grid
        opponent.shipsGrid = opponent.shipsGrid.setCase(opponentGrid, target.x, target.y, missed)
        missed
      case `caseAttacked` if caseAttacked == CaseType.H || caseAttacked == CaseType.M =>
        CaseType.Tried
    }
  }
}
