package player

import grid.CaseType.CaseType
import grid.{CaseType, Grid}
import helpers.BoatType
import helpers.Utils._
import ship.{Position, Ship}

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

  /**
    *
    * @return
    */
  override def notSunkShips(): List[Ship] = {
    ships
  }

  /**
    *
    * @param target
    * @param opponent
    * @return
    */
  override def shoot(target: Position, opponent: Player): CaseType = {
    val opponentGrid = opponent.shipsGrid.grid
    opponentGrid(target.x)(target.y) match {
      case CaseType.S =>
        val hit = CaseType.H
        // update player shotsgrid
        shotsGrid = shotsGrid.setCase(shotsGrid.grid,target.x,target.y,hit)
        // update opponent ships grid
        opponent.shipsGrid = opponent.shipsGrid.setCase(opponentGrid,target.x,target.y,hit)
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
        shotsGrid = shotsGrid.setCase(shotsGrid.grid,target.x,target.y,missed)
        // update opponent ships grid
        opponent.shipsGrid = opponent.shipsGrid.setCase(opponentGrid,target.x,target.y,missed)
        missed
    }
  }

  /**
    *
    * @param target
    * @return
    */
  override def updateShips(target:Position) : Boolean = {
    val shipsToUpdate = ships
    val shipToUpdate = ships.find(s => s.positions.contains(target)).get
    val i = shipsToUpdate.indexOf(shipToUpdate)
    val newShip = shipToUpdate.setShotShip(target)
    val newShips = shipsToUpdate.updated(i,newShip)
    ships = newShips
    newShip.isSunk()
  }
}
