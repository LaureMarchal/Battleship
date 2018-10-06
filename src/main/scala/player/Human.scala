package player

import grid.Grid
import ship.{Position, Ship}

import scala.annotation.tailrec

case class Human(name:String,shipsGrid: Grid, shotsGrid: Grid, livePoints: Int) extends Player {

  override val ships: List[Ship] = Nil

  override def placeShips(): List[Ship] = {
    ships
  }

  override def createShips(): List[Ship] = {
    ships
  }

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
