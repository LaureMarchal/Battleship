package game

import game.Game._

import scala.io.StdIn.readLine

object Main extends App {
  println("Hello ! You are going to play a battleship !")

  //Prompt the user to choose between multi-player and AIs mode
  var gameState = selectModeLoop()
  gameState = placeShipsLoop(gameState)
  println("player 1:\n")
  println("ships :\n")
  println(gameState.getActivePlayer.ships)
  println("grid :\n")
  println(gameState.getActivePlayer.shipsGrid)
  println("player 2:\n")
  println("ships :\n")
  println(gameState.getOpponent.ships)
  println(" grid :\n")
  println(gameState.getOpponent.shipsGrid)
  //gameLoop(gameState)
  //Return winner and ask for rematch
}
