package player

import grid.CaseType.CaseType
import grid.Grid
import helpers.BoatType
import ship.{Position, Ship}

trait Player {
  val name: String
  var livePoints: Int
  var ships: List[Ship]
  var shipsGrid: Grid
  var shotsGrid: Grid

  def placeShips(shipsType: List[BoatType]): List[Ship]
  def notSunkShips(): List[Ship]
  def shoot(target: Position,opponent:Player): CaseType
  def updateShips(target:Position) : Boolean

}
