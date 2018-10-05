package player

import grid.Grid
import ship.Ship
case class Human(name:String,ships: List[Ship],shipsGrid: Grid, shotsGrid: Grid) extends Player {

  override val livePoints: Int = 17
  override val isActive: Boolean = false

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
