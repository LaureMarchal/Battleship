package battleship

import battleship.AIs.{DifficultAI, EasyAI, MediumAI}
import battleship.helpers.{BoatType, CaseType}
import battleship.helpers.Helper._

import scala.annotation.tailrec

/**
  * Object Game that is used to play (create players, gamestate..)
  */
object Game {

  //ships configuration in battleship
  val shipsType : List[BoatType] = getBoatConfiguration()

  /**
    * Create a player with empty grids, live points and a name
    * @return Player
    */
  def createPlayer(playerType:Int): Player = {
    val livePoints = getLivePoints(shipsType)
    val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    val emptyShotsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    playerType match {
      case 0 =>
        val name = getUserNameFromInput()
        Human(name,emptyShipsGrid, emptyShotsGrid, livePoints)
      case 1 =>
        EasyAI(emptyShipsGrid, emptyShotsGrid, livePoints)
      case 2 =>
        MediumAI(emptyShipsGrid, emptyShotsGrid, livePoints)
      case 3 =>
        DifficultAI(emptyShipsGrid, emptyShotsGrid, livePoints)
    }
  }

  /**
    * Function to prompt the user to choose between multi-player and solo mode
    * @return GameState with 2 players (active and opponent)
    */
  def selectModeLoop(): GameState = {
    val mode = getGameModeFromUserInput()
    mode match {
      case 1 =>
        displayModeSelectionChosen(mode)
        val player1 = createPlayer(0)
        displayChangeTurn()
        val player2 = createPlayer(0)
        GameState(player1,player2)
      case 2 =>
        displayModeSelectionChosen(mode)
        val player1 = createPlayer(0)
        val player2 = createPlayer(1)
        GameState(player1,player2)
      case 3 =>
        displayModeSelectionChosen(mode)
        val player1 = createPlayer(0)
        val player2 = createPlayer(2)
        GameState(player1,player2)
      case 4 =>
        displayModeSelectionChosen(mode)
        val player1 = createPlayer(0)
        val player2 = createPlayer(3)
        GameState(player1,player2)
      case _ =>
        displayModeSelectionChosen(mode)
        selectModeLoop()
    }
  }

  /**
    * Function to prompt the players to place their ships
    * @param g gameState (player active or not)
    * @return the new game state with players with placed ships
    */
  def placeShipsLoop(g:GameState): GameState = {
    @tailrec
    def placeShipsRec(g:GameState, nbPlayers: Int): GameState = {
      if (nbPlayers == 0)
        g
      else {
        if (g.getActivePlayer.isInstanceOf[Human]) {
          displayBeforePlacingShip(g.getActivePlayer.name)
          val listShips = g.getActivePlayer.placeShips(shipsType)
          g.getActivePlayer.ships = listShips
          // To display the grid properly to the user
          g.getActivePlayer.shipsGrid.displayGrid()
        } else {
          displayWaitForAI()
          val listShips = g.getActivePlayer.placeShips(shipsType)
          g.getActivePlayer.ships = listShips
        }
        //Clear the console for next player
        clearConsole()
        placeShipsRec(g.switchPlayers, nbPlayers - 1)
      }
    }
    placeShipsRec(g,2)
  }

  /**
    * Function to prompt the user to play
    * @param g gamestate with players updated each turn
    * @return the new gamestate
    */
  def gameLoop(g:GameState): GameState = {
    if (g.getActivePlayer.livePoints == 0)
      g
    else {
      if (g.getActivePlayer.isInstanceOf[Human]) {
        displayPlayerTurn(g.getActivePlayer.name)
        // Show shipsGrid
        g.getActivePlayer.shipsGrid.displayGrid()
        // Show Shots Grid
        g.getActivePlayer.shotsGrid.displayGrid()
      } else {
        displayAITurn()
      }
      // Ask/get target
      val target = g.getActivePlayer.chooseTarget()
      // Shoot
      val shotResult = g.getActivePlayer.shoot(target,g.getOpponent)
      // return result of shot
      shotResult match {
        case CaseType.Sunk => displaySunk()
        case CaseType.X => displayHit()
        case CaseType.O => displayMissed()
        case CaseType.Tried => displayTried()
      }
      //Clear the console for next player
      clearConsole()
      //Switch Players
      gameLoop(g.switchPlayers)
    }
  }

  /**
    * Function to prompt the user if he/she wants to play again or stop the Program
    * @return
    */
  def rematch() : Boolean = {
    val response = askForRematch()
    response match {
      case "replay" => true
      case "quit" => false
      case _ => rematch()
    }
  }
}
