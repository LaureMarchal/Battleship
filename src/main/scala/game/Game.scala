package game

import grid.{CaseType, Grid}
import helpers.Utils._
import player.{AI, Human, Player}

object Game {

  //ships configuration in battleship
  val shipsType : List[Int] = List(2)
  /**
    *
    * @return Player
    */
  def createPlayer(playerType:Int): Player = {
    val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    val emptyShotsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
    playerType match {
      case 0 =>
        val name = getUserNameFromInput()
        println("You are going to place your ship now.\n")
        val hisShips = emptyShipsGrid.placeShips(shipsType)
        Human(name,hisShips,emptyShipsGrid, emptyShotsGrid)
      case 1 =>
        println("You are going to place your ship now.\n")
        val hisShips = emptyShipsGrid.placeShips(shipsType)
        AI(hisShips,emptyShipsGrid, emptyShotsGrid)
    }
  }

  /**
    * Function to prompt the user to choose between multi-player and solo mode
    */
  def selectModeLoop(): Unit = {
    val mode = getGameModeFromUserInput()
    mode match {
      case 1 =>
        println("You chose to play with another Player")
        val player1 = createPlayer(0)
        println(player1.ships)
        //println("And now the next player")
        //val player2 = createPlayer(0)
      case 2 =>
        println("You chose to play against the Easy AI")
      //val player1 = createHumanPlayer()
      //val player2 = createAIPlayer(1)
      case 3 =>
        println("You chose to play against the Intermediate AI")
      //val player1 = createHumanPlayer()
      //val player2 = createAIPlayer(2)
      case 4 =>
        println("You chose to play against the Difficult AI")
      //val player1 = createHumanPlayer()
      //val player2 = createAIPlayer(3)
      case _ =>
        println("That's not a possibility. Try again.")
        selectModeLoop()
    }
  }

  /**
    *
    */
  def placeShipsLoop(): Unit = {
    println("Placing in construction")
    //Place the boats for each player
    //Get boats to place
    //choose one boat
    //place it on grid
    //Stop when no boat to place
  }

  /**
    * GameLoop
    */
  def gameLoop(): Unit = {
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
