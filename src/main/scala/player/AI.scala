package player
import grid.Grid
import ship.Ship

case class AI(shipsGrid: Grid, shotsGrid: Grid, level:Int, livePoints: Int) extends Player {

  override val name: String = "Player AI"
  override var isActive: Boolean = false
  override var ships: List[Ship] = Nil


  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
