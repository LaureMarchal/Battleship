import battleship.helpers.CaseType
import battleship.{GameState, Grid, Human}
import org.scalatest._

class GameStateTest extends FlatSpec with Matchers {

  "getActivePlayer" should "be the first player passed in constructor" in {
    val player = Human("test",Grid(List.fill(10)(List.fill(10)(CaseType.W))),Grid(List.fill(10)(List.fill(10)(CaseType.W))),10)
    val player2 = Human("test2",Grid(List.fill(10)(List.fill(10)(CaseType.W))),Grid(List.fill(10)(List.fill(10)(CaseType.W))),10)

    val gameState1 = GameState(player,player2)

    gameState1.getActivePlayer should be (player)
  }

  "getOpponentPlayer" should "be the second player passed in constructor" in {
    val player = Human("test",Grid(List.fill(10)(List.fill(10)(CaseType.W))),Grid(List.fill(10)(List.fill(10)(CaseType.W))),10)
    val player2 = Human("test2",Grid(List.fill(10)(List.fill(10)(CaseType.W))),Grid(List.fill(10)(List.fill(10)(CaseType.W))),10)

    val gameState1 = GameState(player,player2)

    gameState1.getOpponent should be (player2)
  }

  "switchPlayers" should "exchange players" in {
    val player = Human("test",Grid(List.fill(10)(List.fill(10)(CaseType.W))),Grid(List.fill(10)(List.fill(10)(CaseType.W))),10)
    val player2 = Human("test2",Grid(List.fill(10)(List.fill(10)(CaseType.W))),Grid(List.fill(10)(List.fill(10)(CaseType.W))),10)

    val gameState1 = GameState(player,player2)
    val newGameState = gameState1.switchPlayers

    newGameState.getOpponent should be (gameState1.getActivePlayer)
    newGameState.getActivePlayer should be (gameState1.getOpponent)
  }
}
