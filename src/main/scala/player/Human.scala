package player

import grid.Grid
import helpers.BoatType
import helpers.Utils._
import ship.{Position, Ship}

import scala.annotation.tailrec

case class Human(name:String,shipsGrid: Grid, shotsGrid: Grid, livePoints: Int) extends Player {

  override var ships: List[Ship] = Nil

  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    if (shipsType.isEmpty)
      Nil
    else {
      val newShip = getPositionForShipPlacing(shipsType.head)
      println(s"ship : $newShip")
      newShip::placeShips(shipsType.tail)
    }
  }

  override def createShips(): List[Ship] = {
    ships
  }

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
