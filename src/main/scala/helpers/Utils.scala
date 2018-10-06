package helpers

import player.Player
import ship.{Position, Ship}
import scala.collection.immutable.List
import scala.annotation.tailrec
import scala.io.StdIn.readInt
import scala.io.StdIn.readLine

object Utils {

  /**
    * get a list of the boat to place on grid
    * @return
    */
  def getBoatConfiguration(): List[BoatType] = {
    List(BoatType("Carrier",5),BoatType("Battleship",4),BoatType("Cruiser",3),BoatType("Submarine",3),BoatType("Destroyer",2))
  }

  /**
    * Get the starting live points of player depending on boat config
    * @param config
    * @return
    */
  def getLivePoints(config:List[BoatType]) : Int = {
    if (config.isEmpty)
      0
    else
      config.head.size + getLivePoints(config.tail)
  }

  /**
    * Prompt the user for play mode
    * @return mode
    */
  def getGameModeFromUserInput(): Int = {
    try {
      println("Press the number that corresponds to the desired play mode :\n1 - Multi-player\n2 - VS Easy AI\n3 - VS Intermediate AI\n4 - VS Difficult AI\nmode : ")
      val userInput = readInt()
      userInput
    }
    catch {
      case e: Throwable => {
        println("An error has occured!")
        getGameModeFromUserInput()
      }
    }
  }

  def getUserNameFromInput(): String = {
    val userNameInput = readLine("Enter your player name, please : ")
    userNameInput
  }

  def getPositionsShipFromInput(size:Int): List[Position] = {
    if (size == 0)
      Nil
    else
      println("Next Coordinates (Do not forget each position must be next to each other)")
      val inputPos = readLine("Enter position : ")
      val inputArray = inputPos.split("")
      val inputPosX : Int = inputArray(0).toUpperCase().toInt //Verif
      val inputPosY : Int = inputArray(1).toInt
      val position = Position(inputPosX,inputPosY)
      if (!position.isValidPosition()) {
        println("Not a valid position (out of the grid). Please Try again")
        getPositionsShipFromInput(size)
      }
      else {
        position::getPositionsShipFromInput(size - 1)
      }
  }

  def getDirectionShipFromInput(boatType: BoatType): String = {
    val input = readLine(s"In which direction do you want your ${boatType.name} to go : (Please Answer by N (NORTH), S (SOUTH), E (EAST) or W (WEST)\nDirection : ")
    input match {
      case "N" => input
      case "S" => input
      case "E" => input
      case "W" => input
      case _ =>
        println("This is not a valid direction. Please Try again\n")
        getDirectionShipFromInput(boatType)
    }
  }

  def getPositionForShipPlacing(boatType: BoatType): (Position,String) = {
    println(s"Where do you want to place your ${boatType.name} of size , ${boatType.size} :")
    val validDir = getDirectionShipFromInput(boatType)
    val validPos = getPositionsShipFromInput(boatType.size)

  }

}
