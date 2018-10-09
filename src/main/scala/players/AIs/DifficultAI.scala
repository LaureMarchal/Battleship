package players.AIs

import grid.CaseType.CaseType
import grid.{CaseType, Grid}
import helpers.Helper.{getRandomDirectionStart,  getRandomParityTarget}
import players.Player
import ship.{BoatType, Position, Ship}

import scala.annotation.tailrec

case class DifficultAI(var shipsGrid: Grid, var shotsGrid: Grid, var livePoints: Int) extends Player {

  override val name: String = "Difficult AI"
  override var ships: List[Ship] = Nil
  var lastHitShots:List[Position] = Nil
  var countTriedTarget: Int = 0

  /**
    * Generate the place of a ship depending on AI level
    * @param shipType list of boat type (name,size) to place
    * @return
    */
  def generateShipPlacingAI(shipType: BoatType) : (String,List[Position]) = {
    val (direction,posStart) = getRandomDirectionStart()
    @tailrec
    def generateRandom(direction: String,posStart:Position,size: Int,list:List[Position]) : (String,List[Position]) = {
      if (list.length == size)
        (direction,list)
      else {
        direction match {
          case "N" =>
            val newPos = Position(posStart.y,posStart.x - 1)
            generateRandom(direction,newPos,size,newPos::list)
          case "S" =>
            val newPos = Position(posStart.y,posStart.x + 1)
            generateRandom(direction,newPos,size,newPos::list)
          case "E" =>
            val newPos = Position(posStart.y + 1,posStart.x)
            generateRandom(direction,newPos,size,newPos::list)
          case "W" =>
            val newPos = Position(posStart.y - 1,posStart.x)
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
      val (direction,positions) = generateShipPlacingAI(shipsType.head)
      if (!shipsGrid.isValidPlaceForShip(positions)) {
        placeShips(shipsType)
      } else {
        // create the ship
        val newShip = Ship(shipsType.head.name, direction, positions)
        //place it on the grid
        shipsGrid = shipsGrid.placeOneShip(newShip,shipsGrid.grid)
        //add to player and continue placing ships
        newShip::placeShips(shipsType.tail)
      }
    }
  }

  /**
    * define the hunt when a ship is hit
    * @return the target to shoot
    */
  def generateTargetCloseToLastHit() : Position = {
    val lastHitShot = lastHitShots.head
    countTriedTarget match {
      case 0 =>
        if (!lastHitShot.isLimitPositionMin(lastHitShot.x)) {
          val target = Position(lastHitShot.y,lastHitShot.x - 1)
          val caseAttacked = shotsGrid.grid(target.x)(target.y)
          if (caseAttacked != CaseType.O && caseAttacked != CaseType.X) {
            countTriedTarget += 1
            return target
          }
        }
        countTriedTarget += 1
        generateTargetCloseToLastHit()
      case 1 =>
        if (!lastHitShot.isLimitPositionMax(lastHitShot.x)) {
          val target = Position(lastHitShot.y,lastHitShot.x + 1)
          val caseAttacked = shotsGrid.grid(target.x)(target.y)
          if (caseAttacked != CaseType.O && caseAttacked != CaseType.X) {
            countTriedTarget += 1
            return target
          }
        }
        countTriedTarget += 1
        generateTargetCloseToLastHit()
      case 2 =>
        if (!lastHitShot.isLimitPositionMin(lastHitShot.y)) {
          val target = Position(lastHitShot.y - 1,lastHitShot.x)
          val caseAttacked = shotsGrid.grid(target.x)(target.y)
          if (caseAttacked != CaseType.O && caseAttacked != CaseType.X) {
            countTriedTarget += 1
            return target
          }
        }
        countTriedTarget += 1
        generateTargetCloseToLastHit()
      case 3 =>
        val target = Position(lastHitShot.y + 1,lastHitShot.x)
        val caseAttacked = shotsGrid.grid(target.x)(target.y)
        if (caseAttacked != CaseType.O && caseAttacked != CaseType.X) {
          countTriedTarget += 1
          target
        } else {
          val last = lastHitShots.last
          lastHitShots = last::lastHitShots.dropRight(1)
          generateTargetCloseToLastHit()
        }
      case _ =>
        countTriedTarget = 1
        val last = lastHitShots.last
        lastHitShots = last::lastHitShots.dropRight(1)
        generateTargetCloseToLastHit()
    }
  }

  /**
    * choose or get the target position
    * @return
    */
  override def chooseTarget() : Position = {
    if (lastHitShots.nonEmpty) {
      generateTargetCloseToLastHit()
    }
    else {
      val target = getRandomParityTarget()
      val caseAttacked = shotsGrid.grid(target.x)(target.y)
      if (caseAttacked != CaseType.O && caseAttacked != CaseType.X) target
      else chooseTarget()
    }
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
        val hit = CaseType.X
        // update player shotsgrid
        shotsGrid = shotsGrid.setCase(shotsGrid.grid, target.x, target.y, hit)
        // update opponent ships grid
        opponent.shipsGrid = opponent.shipsGrid.setCase(opponentGrid, target.x, target.y, hit)
        // update opponent ships and check if the ship is sunk
        val isSunkShip = opponent.updateShips(target)
        // update opponent live points
        opponent.livePoints -= 1
        //return the result
        if (isSunkShip) {
          lastHitShots = Nil
          countTriedTarget = 0
          CaseType.Sunk
        }
        else {
          lastHitShots = target::lastHitShots
          if (countTriedTarget > 0) countTriedTarget -= 1
          hit
        }
      case CaseType.W =>
        val missed = CaseType.O
        // update player shotsgrid
        shotsGrid = shotsGrid.setCase(shotsGrid.grid, target.x, target.y, missed)
        // update opponent ships grid
        opponent.shipsGrid = opponent.shipsGrid.setCase(opponentGrid, target.x, target.y, missed)
        missed
      case `caseAttacked` if caseAttacked == CaseType.X || caseAttacked == CaseType.O =>
        CaseType.Tried
    }
  }
}
