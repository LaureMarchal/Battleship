package player

import grid.{CaseType, Grid}
import ship.{Position, Ship}
case class Human(name:String,shipsGrid: Grid, shotsGrid: Grid) extends Player {

  override var livePoints: Int = 17
  override var isActive: Boolean = false
  override var ships: List[Ship] = Nil
  def shipsGrid_=(x$1: grid.Grid): Unit = ???
  def shotsGrid_=(x$1: grid.Grid): Unit = ???

  /**
    *
    * @param positions
    * @param grid
    * @return
    */
  override def placeOneShipOnGrid(positions:List[Position],grid:Grid):Grid = {
    if (positions.isEmpty) {
      grid
    }
    else {
      val firstPos = positions.head
      placeOneShipOnGrid(positions.tail, shipsGrid.updateCaseOnGrid(grid,CaseType.S, firstPos))
    }
  }


  /**
    *
    * @param ships
    * @param grid
    * @return
    */
  override def placeShipsOnGrid(ships:List[Ship], grid: Grid): Grid = {
    if (ships.isEmpty) {
      grid
    }
    else {
      placeOneShipOnGrid(ships.head.positions,grid)
      placeShipsOnGrid(ships.tail,grid)
    }
  }

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
