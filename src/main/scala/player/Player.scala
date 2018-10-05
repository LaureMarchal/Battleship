package player

import grid.Grid
import ship.Ship

trait Player {
  val name: String
  var isActive: Boolean
  var livePoints: Int
  var ships: List[Ship]
  var shipsGrid: Grid
  var shotsGrid: Grid

  def notSunkShips(): List[Ship]
  def shoot(): Int

}
