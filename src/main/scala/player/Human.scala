package player

import grid.Grid
import helpers.BoatType
import helpers.Utils._
import ship.{Position, Ship}

import scala.annotation.tailrec

case class Human(name:String, var shipsGrid: Grid, var shotsGrid: Grid, var livePoints: Int) extends Player {

  override var ships: List[Ship] = Nil

  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    if (shipsType.isEmpty)
      Nil
    else {
      val newShip = getPositionForShipPlacing(shipsType.head)
      if (!shipsGrid.isValidPlaceForShip(newShip)) {
        placeShips(shipsType)
      } else {
        val newGrid = shipsGrid.placeOneShip(newShip,shipsGrid.grid)
        println(s"grid : $newGrid")
        newShip::placeShips(shipsType.tail)
      }
    }
  }

  override def createShips(): List[Ship] = {
    ships
  }

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
