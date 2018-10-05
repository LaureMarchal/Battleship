package player
import grid.Grid
import ships.Ships

case class AI() extends Player {

  override val name: String = "Player AI"
  override val isActive: Boolean = false
  override val livePoints: Int = 17
  override val ships: Ships = null
  override val boatsGrid: Grid = null
  override val shotsGrid: Grid = null

  override def createShips(): AI = ???

  override def notSunkShips(): Ships = ???

  override def shoot(): Int = ???
}
