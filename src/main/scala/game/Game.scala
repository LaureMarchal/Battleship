package game

import helpers.Utils._

object Game {

  /**
    * Function to prompt the user to choose between multi-player and solo mode
    */
  def selectModeLoop(): Unit = {
    val mode = getGameModeFromUserInput()
    mode match {
      case 1 =>
        println("You chose to play with another Player")
        //val player1 = createHumanPlayer()
        //val player2 = createHumanPlayer()
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
