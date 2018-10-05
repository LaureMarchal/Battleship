package helpers

import grid.{CaseType, Grid}
import player.Player
import ship.{Position, Ship}

import scala.annotation.tailrec
import scala.io.StdIn.readInt
import scala.io.StdIn.readLine

object Utils {

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

  def getPositionStartFromInput(size:Int): Position = {
    println(s"Where do you want to place the first case of your ship of size , $size :\nEnter coordinate X : ")
    val inputPosX = readInt()
    println("\nEnter coordinate Y : ")
    val inputPosY = readInt()
    if (inputPosX > 10 || inputPosX < 1) {
      println("This is not a valid direction. Please Try again\n")
      getPositionStartFromInput(size)
    }
    else if (inputPosY > 10 || inputPosY < 1) {
      println("This is not a valid direction. Please Try again\n")
      getPositionStartFromInput(size)
    }
    else
      Position(inputPosX, inputPosY)
  }

  def getDirectionShipFromInput(): String = {
    val input = readLine("In which direction do you want your ship to go : (Please Answer by N (NORTH), S (SOUTH), E (EAST) or W (WEST)\nDirection : ")
    input match {
      case "N" => input
      case "S" => input
      case "E" => input
      case "W" => input
      case _ =>
        println("This is not a valid direction. Please Try again\n")
        getDirectionShipFromInput()
    }
  }

  def getPositionForShipPlacing(size: Int): (Position,String) = {
    val validPos = getPositionStartFromInput(size)
    val validDir = getDirectionShipFromInput()
    validDir match {
      case "N" =>
        if ((validPos.y - (size - 1)) < 1) {
          println("Not possible to place the ship here, not in the grid. Choose a case lower")
          getPositionForShipPlacing(size)
        } else {
          (validPos,validDir)
        }
      case "S" =>
        if ((validPos.y + (size - 1)) > 10) {
          println("Not possible to place the ship here, not in the grid. Choose a case higher")
          getPositionForShipPlacing(size)
        } else {
          (validPos,validDir)
        }
      case "E" =>
        if ((validPos.x + (size - 1)) > 10) {
          println("Not possible to place the ship here, not in the grid. Choose a case more on the left")
          getPositionForShipPlacing(size)
        } else {
          (validPos,validDir)
        }
      case "W" =>
        if ((validPos.x - (size - 1)) < 1) {
          println("Not possible to place the ship here, not in the grid. Choose a case more on the right")
          getPositionForShipPlacing(size)
        } else {
          (validPos,validDir)
        }
    }
  }

}
