package player

import grid.Grid
import helpers.BoatType
import ship.Ship

case class AI(var shipsGrid: Grid, var shotsGrid: Grid, level:Int, var livePoints: Int) extends Player {

  override val name: String = "Player AI"
  override var ships: List[Ship] = Nil

  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    ships
  }

}
