package grid

import grid.CaseType.CaseType

import scala.collection.immutable.List
import scala.annotation.tailrec
import helpers.Utils.getPositionForShipPlacing
import ship.{Position, Ship}

case class Grid(grid: List[List[CaseType]]) {


  // Checking For Placing

  /**
    * Check if a ship is not already on the position
    * @param position
    * @return Bool
    */
  def isValidPositionForShip(position: Position): Boolean = {
    grid(position.x,position.y) match {
      case CaseType.W =>
        if (position.isValidPosition()) true
        else false
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
  def setCase(posX: Int, posY: Int, mark: CaseType): Grid = {
    val myGrid = grid
    val lineToUpdate = myGrid(posX).updated(posY,mark)
    val newGrid = myGrid.updated(posX,lineToUpdate)
    val changedGrid : Grid = copy(grid = newGrid)
    changedGrid
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
        val newGrid = setCase(currentPos.x, currentPos.y, CaseType.S)
        setPositionOnGrid(positions.tail, newGrid.grid)
      }
    }
    setPositionOnGrid(ship.positions,grid)
  }


  //Display on command line

  def displayGrid(grid: Grid): Unit = {
    
  }

  // Useless

    /**
    *
    * @param size
    * @param position
    * @return
    */
  def fillShipN(size: Int,position:Position): List[Position] = {
    if (size == 0)
      Nil
    else {
      val nextPos = Position(position.x,position.y - 1)
      position::fillShipN(size - 1,nextPos)
    }
  }

  /**
    *
    * @param size
    * @param position
    * @return
    */
  def fillShipS(size: Int,position:Position): List[Position] = {
    if (size == 0)
      Nil
    else {
      val nextPos = Position(position.x,position.y + 1)
      position::fillShipN(size - 1,nextPos)
    }
  }

  /**
    *
    * @param size
    * @param position
    * @return
    */
  def fillShipE(size: Int,position:Position): List[Position] = {
    if (size == 0)
      Nil
    else {
      val nextPos = Position(position.x + 1,position.y)
      position::fillShipN(size - 1,nextPos)
    }
  }

  /**
    *
    * @param size
    * @param position
    * @return
    */
  def fillShipW(size: Int,position:Position): List[Position] = {
    if (size == 0)
      Nil
    else {
      val nextPos = Position(position.x - 1,position.y)
      position::fillShipN(size - 1,nextPos)
    }
  }

  /**
    * Place the ship and fill their positions
    * @param size
    * @param position
    * @param direction
    * @return
    */
  def placeShip(size:Int, position: Position, direction: String): Ship = {
    direction match {
      case "N" =>
        Ship(size,isSunk = false, direction,fillShipN(size,position))
      case "S" =>
        Ship(size,isSunk = false, direction,fillShipS(size,position))
      case "E" =>
        Ship(size,isSunk = false,direction,fillShipE(size,position))
      case "W" =>
        Ship(size,isSunk = false,direction,fillShipW(size,position))
    }
  }

  /**
    * Create a List of ship depending on the config of the battleship
    * And Ask user to place his ships
    * @param shipsType List[Int] config of battleship
    * @return
    */
  def placeShips(shipsType: List[Int]):List[Ship] = {
    if (shipsType.isEmpty) {
      Nil
    } else {
      val size = shipsType.head
      println(s"Placing your ship of size , $size : ")
      val (pos,dir) = getPositionForShipPlacing(size)
      placeShip(size,pos,dir)::placeShips(shipsType.tail)
    }
  }
}
