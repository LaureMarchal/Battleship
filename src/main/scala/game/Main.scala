package game

import game.Game._
import grid.{CaseType, Grid}
import helpers.Helper._
import players.AIs.EasyAI
import players.Human
import ship.BoatType

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
    println("Let's Play")
    gameState = gameLoop(gameState)
    displayWinner(gameState)
    // Ask for replay
    if (rematch()) mainLoop()
  }

  /*
  // Start the game
  println("Hello ! You are going to play a battleship !")
  mainLoop()*/
  val shipsType : List[BoatType] = List(BoatType("Destroyer",2))
  val livePoints = getLivePoints(shipsType)
  val emptyShipsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
  val emptyShotsGrid = Grid(List.fill(10)(List.fill(10)(CaseType.W)))
  val test = EasyAI(emptyShipsGrid, emptyShotsGrid,1, livePoints)

  val direction,positions = test.generateShipPlacingAI(BoatType("Destroyer",2),1)
}
