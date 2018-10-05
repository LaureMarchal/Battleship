package player

import grid.Grid
import ship.{Position, Ship}

trait Player {
  val name: String
  var isActive: Boolean
  var livePoints: Int
  var ships: List[Ship]
  var shipsGrid: Grid
  var shotsGrid: Grid

  def placeOneShipOnGrid(positions:List[Position],grid:Grid):Grid
  def placeShipsOnGrid(ships:List[Ship], grid: Grid): Grid
  def notSunkShips(): List[Ship]
  def shoot(): Int

}
