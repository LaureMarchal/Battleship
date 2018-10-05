package game

import game.Game._

object Main extends App {
  println("Hello ! You are going to play a battleship !")

  //Prompt the user to choose between multi-player and AIs mode
  selectModeLoop()
  placeShipsLoop()
  gameLoop()
  //Return winner and ask for rematch
}
