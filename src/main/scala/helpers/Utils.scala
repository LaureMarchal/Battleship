package helpers

import java.lang.Exception

import player.Player
import ship.{Position, Ship}

import scala.collection.immutable.List
import scala.annotation.tailrec
import scala.io.StdIn.readInt
import scala.io.StdIn.readLine
import scala.util.control.Exception

object Utils {

  val mod : Int = 'A'.toInt

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

  /**
    * ask the name of the player
    * @return
    */
  def getUserNameFromInput(): String = {
    val userNameInput = readLine("Enter your player name, please : ")
    userNameInput
  }

  /**
    * Ask for positions for a ship
    * @param size
    * @return
    */
  def getPositionsShipFromInput(size:Int): List[Position] = {
    if (size == 0)
      Nil
    else {
      val inputPos = readLine("Enter position (must be between 1 and 10 or A and J => example : A 9 ) : ")
      try {
        val inputArray = inputPos.split(" ")
        val inputX = inputArray(0).toUpperCase().toCharArray.head.toInt
        val inputPosX = inputX - mod + 1
        val inputPosY : Int = inputArray(1).toInt
        val position = Position(inputPosX,inputPosY)
        if (!position.isValidPosition()) {
          println("Not a valid position (out of the grid). Please Try again")
          getPositionsShipFromInput(size)
        } else {
          println(s"x : $inputPosX, y : $inputPosY")
          val newSize = size - 1
          println(s"$newSize positions left")
          getPositionsShipFromInput(newSize):+position
        }
      } catch {
        case e: Exception =>
          println("A problem occurred. Make sure you wrote the position like the example.")
          getPositionsShipFromInput(size)
      }
    }
  }

  /**
    * Ask the direction of the ship
    * @param boatType
    * @return
    */
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

  /**
    * Check if a list of positions is next to each other
    * @param positions
    * @param direction
    * @return
    */
  def isValidPositionList(positions :List[Position], direction : String): Boolean = {
    if (positions.size == 1)
      true
    else {
      val pos1 = positions.head
      val positionsChanged = positions.tail
      val pos2 = positionsChanged.head
      direction match {
        case "N" =>
          val diff = Math.abs(pos1.y - pos2.y)
          if (pos1.x != pos2.x && diff != 1)
            false
          else
            isValidPositionList(positionsChanged, direction)
        case "S" =>
          val diff = Math.abs(pos1.y - pos2.y)
          if (pos1.x != pos2.x && diff != 1)
            false
          else
            isValidPositionList(positionsChanged, direction)
        case "E" =>
          val diff = Math.abs(pos1.x - pos2.x)
          if (pos1.y != pos2.y && diff != 1)
            false
          else
            isValidPositionList(positionsChanged, direction)
        case "W" =>
          val diff = Math.abs(pos1.x - pos2.x)
          if (pos1.y != pos2.y && diff != 1)
            false
          else
            isValidPositionList(positionsChanged, direction)
      }
    }
  }

  /**
    * Ask to place the ships
    * @param boatType
    */
  def getPositionForShipPlacing(boatType: BoatType): Ship = {
    println(s"Place your ${boatType.name} of size ${boatType.size} :")
    val validDirection = getDirectionShipFromInput(boatType)
    val positionsInput = getPositionsShipFromInput(boatType.size)
    println(positionsInput)
    if (isValidPositionList(positionsInput,validDirection)) {
      println("create the ship")
      Ship(boatType.name,boatType.size,validDirection,positionsInput)
    } else {
      println("The positions for the ship are not in the right configuration, please try again")
      getPositionForShipPlacing(boatType)
    }
  }

}
