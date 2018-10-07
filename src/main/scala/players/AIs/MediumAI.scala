package players.AIs

import grid.CaseType.CaseType
import grid.{CaseType, Grid}
import helpers.Helper.getRandomTarget
import players.Player
import ship.{BoatType, Position, Ship}

import scala.util.Random

case class MediumAI(var shipsGrid: Grid, var shotsGrid: Grid, var livePoints: Int) extends Player {

  override val name: String = "Medium AI"
  override var ships: List[Ship] = Nil
  var lastHitShot:Position = _
  var countTriedTarget: Int = 0


  /**
    * Generate the place of a ship depending on AI level
    * @param shipType list of boat type (name,size) to place
    * @return
    */
  def generateShipPlacingAI(shipType: BoatType) : (String,List[Position]) = {
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
      case _ => generateShipPlacingAI(shipType)
    }
    val positions = List.fill(shipType.size)(posFirst)
    (direction,positions)
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
    if (lastHitShot == null) {
      val target = getRandomTarget()
      val caseAttacked = shotsGrid.grid(target.x)(target.y)
      if (caseAttacked == CaseType.M || caseAttacked == CaseType.H) {
        chooseTarget()
      } else
        target
    } else {
      val limitGridMinX = lastHitShot.isLimitPositionMin(lastHitShot.x)
      val limitGridMinY = lastHitShot.isLimitPositionMin(lastHitShot.y)
      val limitGridMaxY = lastHitShot.isLimitPositionMax(lastHitShot.y)
      countTriedTarget match {
        case 0 =>
          countTriedTarget += 1
          if (!limitGridMinY) {
            val target = Position(lastHitShot.x,lastHitShot.y - 1)
            val caseAttacked = shotsGrid.grid(target.x)(target.y)
            if (caseAttacked == CaseType.M || caseAttacked == CaseType.H) {
              countTriedTarget += 1
              chooseTarget()
            } else {
              target
            }
          } else {
            countTriedTarget += 1
            chooseTarget()
          }
        case 1 =>
          countTriedTarget += 1
          if (!limitGridMaxY) {
            val target = Position(lastHitShot.x,lastHitShot.y + 1)
            val caseAttacked = shotsGrid.grid(target.x)(target.y)
            if (caseAttacked == CaseType.M || caseAttacked == CaseType.H) {
              countTriedTarget += 1
              chooseTarget()
            } else {
              target
            }
          } else {
            countTriedTarget += 1
            chooseTarget()
          }
        case 2 =>
          countTriedTarget += 1
          if (!limitGridMinX) {
            val target = Position(lastHitShot.x - 1,lastHitShot.y)
            val caseAttacked = shotsGrid.grid(target.x)(target.y)
            if (caseAttacked == CaseType.M || caseAttacked == CaseType.H) {
              countTriedTarget += 1
              chooseTarget()
            } else {
              target
            }
          } else {
            countTriedTarget += 1
            chooseTarget()
          }
        case 3 =>
          countTriedTarget = 0
          Position(lastHitShot.x + 1,lastHitShot.y)
      }
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
        if (isSunkShip) {
          lastHitShot = null
          countTriedTarget = 0
          CaseType.Sunk
        }
        else
          lastHitShot = target
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
