package player

import grid.Grid
import ship.Ship

trait Player {
  val name: String
  val isActive: Boolean
  val livePoints: Int
  val ships: List[Ship]
  val shipsGrid: Grid
  val shotsGrid: Grid

  def notSunkShips(): List[Ship]
  def shoot(): Int

}
