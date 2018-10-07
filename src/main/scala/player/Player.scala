package player

import grid.CaseType.CaseType
import grid.{CaseType, Grid}
import helpers.BoatType
import ship.{Position, Ship}

trait Player {
  val name: String
  var livePoints: Int
  var ships: List[Ship]
  var shipsGrid: Grid
  var shotsGrid: Grid

  /**
    *
    * @param shipsType
    * @return
    */
  def placeShips(shipsType: List[BoatType]): List[Ship]

  /**
    *
    * @return
    */
  def notSunkShips(): List[Ship] = ships

  /**
    *
    * @param target
    * @param opponent
    * @return
    */
  def shoot(target: Position,opponent:Player): CaseType = {
    val opponentGrid = opponent.shipsGrid.grid
    opponentGrid(target.x)(target.y) match {
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
    }
  }

  /**
    *
    * @param target
    * @return
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
