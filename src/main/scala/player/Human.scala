package player

import grid.Grid
import ship.Ship

case class Human(name:String,shipsGrid: Grid, shotsGrid: Grid, livePoints: Int) extends Player {

  override var isActive: Boolean = false
  override var ships: List[Ship] = Nil


  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
