package battleship

import battleship.Game._
import battleship.helpers.Helper._

/**
  * Main object to launch the game
  */
object Main extends App {

  /**
    * Main loop to play battleship as many times as user wants
    */
  def mainLoop(): Unit = {
    var gameState = selectModeLoop()
    gameState = placeShipsLoop(gameState)
    displayBeforePlaying()
    gameState = gameLoop(gameState)
    displayWinner(gameState)
    // Ask for replay
    if (rematch()) mainLoop()
  }

  // Start the game
  displayTitle()
  mainLoop()
}
