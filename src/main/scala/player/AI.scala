package player
import grid.Grid
import ship.Ship

case class AI(ships: List[Ship],shipsGrid: Grid, shotsGrid: Grid) extends Player {

  override val name: String = "Player AI"
  override val isActive: Boolean = false
  override val livePoints: Int = 17

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
