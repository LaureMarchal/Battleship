package player
import grid.CaseType.CaseType
import grid.Grid
import helpers.BoatType
import ship.{Position, Ship}

case class AI(var shipsGrid: Grid, var shotsGrid: Grid, level:Int, var livePoints: Int) extends Player {

  override val name: String = "Player AI"
  override var ships: List[Ship] = Nil

  override def placeShips(shipsType: List[BoatType]): List[Ship] = {
    ships
  }

  override def notSunkShips(): List[Ship] = ???

  override def shoot(target: Position,opponent: Player): CaseType = ???

  def updateShips(target:Position) : Boolean = ???
}
