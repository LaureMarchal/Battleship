package game

import game.Game._
import grid.{CaseType, Grid}
import helpers.Helper._
import ship.{Position, Ship}

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
  println("Hello ! You are going to play a battleship !")
  mainLoop()
}
