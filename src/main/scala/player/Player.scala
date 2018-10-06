package player

import grid.Grid
import ship.{Position, Ship}

trait Player {
  val name: String
  val livePoints: Int
  val ships: List[Ship]
  val shipsGrid: Grid
  val shotsGrid: Grid

  def createShips(): List[Ship]
  def placeShips(positions:List[Position], ships:List[Ship]): List[Ship]
  def notSunkShips(): List[Ship]
  def shoot(): Int

}
