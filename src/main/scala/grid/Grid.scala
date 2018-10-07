package grid

import grid.CaseType.CaseType

import scala.collection.immutable.List
import scala.annotation.tailrec
import ship.{Position, Ship}

case class Grid(grid: List[List[CaseType]]) {


  // Checking For Placing

  /**
    * Check if a ship is not already on the position
    * @param position
    * @return Bool
    */
  def isValidPositionForShip(position: Position): Boolean = {
    grid(position.x)(position.y) match {
      case CaseType.W => true
      case _ => false
    }
  }

  /**
    * check if a ship can be placed on those positions
    * @param ship
    * @return
    */
  def isValidPlaceForShip(ship: Ship): Boolean = {
    @tailrec
    def areAllPositionsValid(positions: List[Position]): Boolean = {
      if (positions.isEmpty)
        true
      else
      if (!isValidPositionForShip(positions.head))
        false
      else
        areAllPositionsValid(positions.tail)
    }
    areAllPositionsValid(ship.positions)
  }

  // Placing on grid

  /**
    * set a case of the grid
    * @param posX
    * @param posY
    * @param mark
    * @return grid
    */
  def setCase(gridToUpdate: List[List[CaseType]],posX: Int, posY: Int, mark: CaseType): Grid = {
    val myGrid = gridToUpdate
    val lineToUpdate = myGrid(posX).updated(posY,mark)
    val newGrid = Grid(myGrid.updated(posX,lineToUpdate))
    newGrid
  }

  /**
    * place One ship at a time
    * @param ship
    * @param grid
    * @return
    */
  def placeOneShip(ship:Ship, grid: List[List[CaseType]]):Grid = {
    @tailrec
    def setPositionOnGrid(positions: List[Position], changedGrid: List[List[CaseType]]): Grid = {
      if (positions.isEmpty)
        copy(grid = changedGrid)
      else {
        val currentPos = positions.head
        val newGrid = setCase(changedGrid,currentPos.x, currentPos.y, CaseType.S)
        setPositionOnGrid(positions.tail, newGrid.grid)
      }
    }
    setPositionOnGrid(ship.positions,grid)
  }


  //Display on command line

  def displayGrid(): Unit = {
    println(s"\n $grid")
  }
}
