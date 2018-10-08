import grid.Grid
import grid.CaseType
import ship.Position
import org.scalatest._

class GridTest extends FlatSpec with Matchers {

  "isValidPositionForShip" should "true if no ship there yet at the tested position" in {
    val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    val fullGrid = Grid(List.fill(10)(List.fill(10)(CaseType.S)))

    val lineToUpdate = fullGrid.grid(4).updated(2, CaseType.W)
    val fullExceptOne_Grid = Grid(fullGrid.grid.updated(4,lineToUpdate))

    val lineToUpdate2 = emptyShipsGrid.grid(4).updated(2, CaseType.S)
    val emptyExceptOne_Grid = Grid(emptyShipsGrid.grid.updated(4,lineToUpdate2))

    val position = Position(4, 2)

    emptyShipsGrid.isValidPositionForShip(position) should be (true)
    fullGrid.isValidPositionForShip(position) should be (false)

    fullExceptOne_Grid.isValidPositionForShip(position) should be (true)
    emptyExceptOne_Grid.isValidPositionForShip(position) should be (false)
  }



}
