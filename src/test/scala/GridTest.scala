import battleship.{Grid, Position, Ship}
import battleship.helpers.CaseType
import org.scalatest._

class GridTest extends FlatSpec with Matchers {

  "isValidPositionForShip" should "true if no ship there yet at the tested position" in {
    val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    val fullGrid = Grid(List.fill(10)(List.fill(10)(CaseType.S)))

    val lineToUpdate = fullGrid.grid(2).updated(4, CaseType.W)
    val fullExceptOne_Grid = Grid(fullGrid.grid.updated(2,lineToUpdate))

    val lineToUpdate2 = emptyShipsGrid.grid(2).updated(4, CaseType.S)
    val emptyExceptOne_Grid = Grid(emptyShipsGrid.grid.updated(2,lineToUpdate2))

    val position = Position(4, 2)

    emptyShipsGrid.isValidPositionForShip(position) should be (true)
    fullGrid.isValidPositionForShip(position) should be (false)

    fullExceptOne_Grid.isValidPositionForShip(position) should be (true)
    emptyExceptOne_Grid.isValidPositionForShip(position) should be (false)
  }


  "isValidPlaceForShip" should "true if a ship can be placed on those positions" in {
    val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    val fullGrid = Grid(List.fill(10)(List.fill(10)(CaseType.S)))

    val lineToUpdate = fullGrid.grid(2).updated(4, CaseType.W)
    val fullExceptOne_Grid = Grid(fullGrid.grid.updated(2,lineToUpdate))
    val lineToUpdate2 = fullExceptOne_Grid.grid(3).updated(4, CaseType.W)
    val fullExceptTwo_Grid = Grid(fullExceptOne_Grid.grid.updated(3,lineToUpdate2))

    val lineToUpdate3 = emptyShipsGrid.grid(2).updated(4, CaseType.S)
    val emptyExceptOne_Grid = Grid(emptyShipsGrid.grid.updated(2,lineToUpdate3))

    val positions = List(Position(4, 2), Position(4, 3))

    emptyShipsGrid.isValidPlaceForShip(positions) should be (true)
    fullGrid.isValidPlaceForShip(positions) should be (false)

    fullExceptTwo_Grid.isValidPlaceForShip(positions) should be (true)
    emptyExceptOne_Grid.isValidPlaceForShip(positions) should be (false)
  }

  "setCase" should "update the grid at the position and caseType" in {
    val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    val fullGrid = Grid(List.fill(10)(List.fill(10)(CaseType.S)))

    val lineToUpdate3 = emptyShipsGrid.grid(4).updated(2, CaseType.S)
    val emptyExceptOne_Grid = Grid(emptyShipsGrid.grid.updated(4,lineToUpdate3))

    val lineToUpdate = fullGrid.grid(4).updated(2, CaseType.W)
    val fullExceptOne_Grid = Grid(fullGrid.grid.updated(4,lineToUpdate))

    val lineToUpdate2 = fullExceptOne_Grid.grid(4).updated(3, CaseType.W)
    val fullExceptTwo_Grid = Grid(fullExceptOne_Grid.grid.updated(4,lineToUpdate2))

    val newGridEmptyExcept = emptyShipsGrid.setCase(emptyShipsGrid.grid,4,2,CaseType.S)
    val newGridFullExcept = fullGrid.setCase(fullGrid.grid,4,2,CaseType.W)
    val newGridFullExcept_Two = newGridFullExcept.setCase(newGridFullExcept.grid,4,3,CaseType.W)

    newGridEmptyExcept should be (emptyExceptOne_Grid)
    newGridFullExcept should be (fullExceptOne_Grid)
    newGridFullExcept_Two should be (fullExceptTwo_Grid)
  }

  "placeOneShip" should "place ship on grid at its positions" in {
    val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))

    val ship = Ship("test","S",List(Position(4,2),Position(4,3)))

    val lineToUpdate = emptyShipsGrid.grid(2).updated(4, CaseType.S)
    val oneShipGrid_1 = Grid(emptyShipsGrid.grid.updated(2,lineToUpdate))
    val lineToUpdate2 = oneShipGrid_1.grid(3).updated(4, CaseType.S)
    val oneShipGrid = Grid(oneShipGrid_1.grid.updated(3,lineToUpdate2))

    val newOneShipGrid = emptyShipsGrid.placeOneShip(ship,emptyShipsGrid.grid)

    newOneShipGrid should be (oneShipGrid)
  }

}
