package player
import grid.Grid
import ship.{Position, Ship}

case class AI(shipsGrid: Grid, shotsGrid: Grid, level:Int, livePoints: Int) extends Player {

  override val name: String = "Player AI"
  override val ships: List[Ship] = Nil

  override def placeShips(positions:List[Position], ships:List[Ship]): List[Ship] = {
    ships
  }
  override def createShips(): List[Ship] = ???

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
