package player

import grid.Grid
import helpers.BoatType
import ship.{Position, Ship}

trait Player {
  val name: String
  var livePoints: Int
  var ships: List[Ship]
  var shipsGrid: Grid
  var shotsGrid: Grid

  def createShips(): List[Ship]
  //def placeShips(positions:List[Position], ships:List[Ship]): List[Ship]
  def placeShips(shipsType: List[BoatType]): List[Ship]
  def notSunkShips(): List[Ship]
  def shoot(): Int

}
