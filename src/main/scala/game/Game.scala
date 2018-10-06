package game

import grid.{CaseType, Grid}
import helpers.BoatType
import helpers.Utils._
import player.{AI, Human, Player}

import scala.annotation.tailrec

object Game {

  //ships configuration in battleship
  //val shipsType : List[BoatType] = getBoatConfiguration()
  val shipsType : List[BoatType] = List(BoatType("Submarine",3),BoatType("Destroyer",2))
  /**
    *
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
        AI(emptyShipsGrid, emptyShotsGrid,1, livePoints)
      case 2 =>
        AI(emptyShipsGrid, emptyShotsGrid,2, livePoints)
      case 3 =>
        AI(emptyShipsGrid, emptyShotsGrid,3, livePoints)
    }
  }

  /**
    * Function to prompt the user to choose between multi-player and solo mode
    */
  def selectModeLoop(): GameState = {
    val mode = getGameModeFromUserInput()
    mode match {
      case 1 =>
        println("You chose to play with another Player")
        val player1 = createPlayer(0)
        println(player1.ships)
        println("And now the next player")
        val player2 = createPlayer(0)
        GameState(player1,player2)
      case 2 =>
        println("You chose to play against the Easy AI")
        val player1 = createPlayer(0)
        val player2 = createPlayer(1)
        GameState(player1,player2)
      case 3 =>
        println("You chose to play against the Intermediate AI")
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
    *
    */
  def placeShipsLoop(g:GameState): GameState = {
    @tailrec
    def placeShipsRec(g:GameState, nbPlayers: Int): GameState = {
      if (nbPlayers == 0)
        g
      else {
        val name = g.getActivePlayer.name
        println(s"Player $name. It's your turn !\n")
        println("You are going to place your ships.\n")
        val listShips = g.getActivePlayer.placeShips(shipsType)
        g.getActivePlayer.ships = listShips
        //g.getActivePlayer.shipsGrid = g.getActivePlayer.placeShipsOnGrid(g.getActivePlayer.ships, g.getActivePlayer.shipsGrid)
        placeShipsRec(g.switchPlayers, nbPlayers - 1)
      }
    }
    placeShipsRec(g,2)
  }

  /**
    * GameLoop
    */
  def gameLoop(g:GameState): Unit = {
    println("game in construction")
    //Choose first player
    //Show grid of boats
    //Prompt player for target
    //show grid of shots
    //shoot target
    //If player win
    //end game
    //else
    //switch player
  }
}
