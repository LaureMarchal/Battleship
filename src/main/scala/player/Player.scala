package player

import grid.CaseType.CaseType
import grid.{CaseType, Grid}
import helpers.BoatType
import ship.{Position, Ship}

/**
  * Player behavior
  */
trait Player {

  val name: String
  var livePoints: Int
  var ships: List[Ship]
  var shipsGrid: Grid
  var shotsGrid: Grid

  /**
    * Place the ships of a player
    * @param shipsType list of boat type (name,size) that define a ship to play with
    * @return
    */
  def placeShips(shipsType: List[BoatType]): List[Ship]

  /**
    * Return the ships that are not sunk
    * @return list of not sunk ships
    */
  def notSunkShips(): List[Ship] = ships.filterNot(x => x.size == 0)

  /**
    * Shoot a target
    * @param target position to shoot
    * @param opponent the player who is attacked
    * @return the CaseType : result of the shot
    */
  def shoot(target: Position,opponent:Player): CaseType = {
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

  /**
    * update the ships of the player if the target was a ship position
    * @param target position hit
    * @return true if the ship is sunk or else false
    */
  def updateShips(target:Position) : Boolean = {
    val shipsToUpdate = ships
    val shipToUpdate = ships.find(s => s.positions.contains(target)).get
    val i = shipsToUpdate.indexOf(shipToUpdate)
    val newShip = shipToUpdate.setShotShip(target)
    val newShips = shipsToUpdate.updated(i,newShip)
    ships = newShips
    newShip.isSunk()
  }

}
