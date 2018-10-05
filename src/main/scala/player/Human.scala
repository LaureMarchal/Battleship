package player

import grid.Grid
import ships.Ships

case class Human() extends Player {

  override val name: String = "Player human"
  override val livePoints: Int = 17
  override val ships: Ships = null
  override val boatsGrid: Grid = null
  override val shotsGrid: Grid = null
  override val isActive: Boolean = false

  override def createShips(): Human = ???

  override def notSunkShips(): Ships = ???

  override def shoot(): Int = ???
}
