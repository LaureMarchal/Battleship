package game

import grid.{CaseType, Grid}
import helpers.Helper._
import players.AIs.{DifficultAI, EasyAI, MediumAI}
import players.{Human, Player}
import ship.BoatType

import scala.annotation.tailrec

/**
  * Object Game that is used to play (create players, gamestate..)
  */
object Game {

  //ships configuration in battleship
  //val shipsType : List[BoatType] = getBoatConfiguration()
  val shipsType : List[BoatType] = List(BoatType("Submarine",3),BoatType("Destroyer",2))
  //val shipsType : List[BoatType] = List(BoatType("Destroyer",2))

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
        println("You chose to play with another Player")
        val player1 = createPlayer(0)
        println("And now the next player")
        val player2 = createPlayer(0)
        GameState(player1,player2)
      case 2 =>
        println("You chose to play against the Easy AI")
        val player1 = createPlayer(0)
        val player2 = createPlayer(1)
        GameState(player1,player2)
      case 3 =>
        println("You chose to play against the Medium AI")
        val player1 = createPlayer(0)
        val player2 = createPlayer(2)
        GameState(player1,player2)
      case 4 =>
        println("You chose to play against the Difficult AI")
        val player1 = createPlayer(0)
        val player2 = createPlayer(3)
        GameState(player1,player2)
      case _ =>
        println("That's not a possibility. Try again.")
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
          val name = g.getActivePlayer.name
          println(s"Player $name. It's your turn !\n")
          println("You are going to place your ships.\n")
          displayBeforePlacingShip()
          val listShips = g.getActivePlayer.placeShips(shipsType)
          g.getActivePlayer.ships = listShips
          // To display the grid properly to the user
          g.getActivePlayer.shipsGrid.displayGrid()
        } else {
          println("Please wait. The AI is placing its ships...\n")
          val listShips = g.getActivePlayer.placeShips(shipsType)
          g.getActivePlayer.ships = listShips
        }
        //Clear the console for next player
        print("\033[H\033[2J")
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
        val name = g.getActivePlayer.name
        println(s"\nPlayer $name. It's your turn !\n")
        // Show shipsGrid
        println("Your ships Grid :\n")
        g.getActivePlayer.shipsGrid.displayGrid()
        // Show Shots Grid
        println("Your shots Grid :\n")
        g.getActivePlayer.shotsGrid.displayGrid()
      } else {
        println(s"\nIt's the AI turn !\n")
        println(g.getActivePlayer.ships)
      }
      // Ask/get target
      val target = g.getActivePlayer.chooseTarget()
      // Shoot
      val shotResult = g.getActivePlayer.shoot(target,g.getOpponent)
      // return result of shot
      shotResult match {
        case CaseType.Sunk => displaySunk()
        case CaseType.H => displayHit()
        case CaseType.M => displayMissed()
        case CaseType.Tried => displayTried()
      }
      //Clear the console for next player
      print("\033[H\033[2J")
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
