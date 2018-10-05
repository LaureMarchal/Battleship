package player

import grid.Grid
import ships.Ships

trait Player {
  val name: String
  val isActive: Boolean
  val livePoints: Int
  val ships: Ships
  val boatsGrid: Grid
  val shotsGrid: Grid

  //def createPlayer(): Player
  def createShips(): Player
  def notSunkShips(): Ships
  def shoot(): Int

}
