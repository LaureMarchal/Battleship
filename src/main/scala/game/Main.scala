package game

import game.Game._
import helpers.Utils._

object Main extends App {
  println("Hello ! You are going to play a battleship !")

  def mainLoop(): Unit = {
    //Prompt the user to choose between multi-player and AIs mode
    var gameState = selectModeLoop()
    gameState = placeShipsLoop(gameState)
    println("Let's Play")
    gameState = gameLoop(gameState)
    //Return winner and ask for rematch
    displayWinner(gameState)
    if (rematch())
      mainLoop()
  }

  // Start the game
  mainLoop()
}
