package battleship

import battleship.helpers.CaseType
import battleship.helpers.CaseType.CaseType

import scala.annotation.tailrec
import scala.collection.immutable.List

/**
  * Grid that contains the list of list of case (which have a particular type)
  * @param grid list of list
  */
case class Grid(grid: List[List[CaseType]]) {


  // Checking For Placing

  /**
    * Check if a ship is not already on the position
    * @param position (x,y)
    * @return true if not ship there yet or else false
    */
  def isValidPositionForShip(position: Position): Boolean = {
    grid(position.x)(position.y) match {
      case CaseType.W => true
      case _ => false
    }
  }

  /**
    * check if a ship can be placed on those positions
    * @param positions list of (x,y)
    * @return true is each position is valid or else false
    */
  def isValidPlaceForShip(positions: List[Position]): Boolean = {
    if (positions.isEmpty)
      true
    else {
      if (!isValidPositionForShip(positions.head))
        false
      else
        isValidPlaceForShip(positions.tail)
    }
  }

  // Placing on grid

  /**
    * set a case of the grid to a type
    * @param posX x int
    * @param posY y int
    * @param mark type of the case to set
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
    * @param ship to place (add in ships of player and shipsGrid)
    * @param grid to update (shipsGrid of the player)
    * @return the updated shipsGrid
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

  /**
    * User-friendly way of showing a grid to players
    */
  def displayGrid(): Unit = {
    println("\n     A   B   C   D   E   F   G   H   I   J  ")
    println("   -----------------------------------------")
    @tailrec
    def displayLineGrid(grid: List[List[CaseType]], index: Int) : Unit = {
      if (grid.isEmpty)
        println("")
      else {
        val lineToDisplay = grid.head
        if (index == 10) print(s"$index ") else print(s"$index  ")
        @tailrec
        def displayCaseGrid(line: List[CaseType]) : Unit = {
          if (line.isEmpty) {
            print("|")
            println("\n   -----------------------------------------")
          } else {
            val pos = line.head
            print(s"| $pos ")
            displayCaseGrid(line.tail)
          }
        }
        displayCaseGrid(lineToDisplay)
        displayLineGrid(grid.tail,index + 1)
      }
    }
    displayLineGrid(grid,1)
  }
}
