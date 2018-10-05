package grid

import grid.CaseType.CaseType
import helpers.Utils.getPositionForShipPlacing
import ship.{Position, Ship}

case class Grid(grid: List[List[CaseType]]) {


  def updateCaseOnGrid(grid: Grid, caseType: CaseType, position: Position):Grid = {
    var gridCopy = grid.copy()
    println(gridCopy.grid)
    grid
  }
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
        Ship(size,isSunk = false,fillShipN(size,position))
      case "S" =>
        Ship(size,isSunk = false,fillShipS(size,position))
      case "E" =>
        Ship(size,isSunk = false,fillShipE(size,position))
      case "W" =>
        Ship(size,isSunk = false,fillShipW(size,position))
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
