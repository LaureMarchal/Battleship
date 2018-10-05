package player
import grid.Grid
import ship.{Position, Ship}

case class AI(shipsGrid: Grid, shotsGrid: Grid, level:Int) extends Player {

  override val name: String = "Player AI"
  override var isActive: Boolean = false
  override var livePoints: Int = 17
  override var ships: List[Ship] = Nil
  def shipsGrid_=(x$1: grid.Grid): Unit = ???
  def shotsGrid_=(x$1: grid.Grid): Unit = ???


  override def placeOneShipOnGrid(positions:List[Position],grid:Grid):Grid = ???

  override def placeShipsOnGrid(ships:List[Ship], grid: Grid): Grid = ???

  override def notSunkShips(): List[Ship] = ???

  override def shoot(): Int = ???
}
