package battleship.helpers

import battleship.{GameState, Position}

import scala.collection.immutable.List
import scala.io.StdIn.{readInt, readLine}
import scala.util.Random

/**
  * Object Helper that contains each function to prompt the user, print on console or specific to battleship game
  */
object Helper {

  /**
    * mod is the modulo to convert the letter to an Int case
    */
  val mod : Int = 'A'.toInt

  // functions for battle configuration

  /**
    * get a list of the boat to place on grid
    * @return the list of boat for the game
    */
  def getBoatConfiguration(): List[BoatType] = {
    List(BoatType("Carrier",5),BoatType("Battleship",4),BoatType("Cruiser",3),BoatType("Submarine",3),BoatType("Destroyer",2))
  }

  /**
    * Get the starting live points of player depending on boat config
    * @param config the list of ship to play with
    * @return the live points of each player
    */
  def getLivePoints(config:List[BoatType]) : Int = {
    if (config.isEmpty)
      0
    else
      config.head.size + getLivePoints(config.tail)
  }

  // AI helpers functions

  /**
    * get a random position with an even int in it
    * @return
    */
  def getRandomParityTarget(): Position = {
    val random = new Random()
    val x = random.nextInt(9)
    val y = random.nextInt(9)
    val toTry = random.nextInt(2)
    if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) if (toTry == 0) Position(x, y + 1) else  Position(x + 1, y)
    else Position(x, y)
  }
  /**
    * get a random target
    * @return position to shoot
    */
  def getRandomTarget(): Position = {
    val random = new Random()
    val pos = Position(random.nextInt(10),random.nextInt(10))
    if (pos.isValidPosition()) pos
    else getRandomTarget()
  }

  /**
    * get a random direction to place a ship
    * @return
    */
  def getRandomDirectionStart(): (String,Position) = {
    val random = new Random()
    val dir = random.nextInt(4)
    dir match {
      case 0 => ("N",Position(random.nextInt(10),9))
      case 1 => ("S",Position(random.nextInt(10),0))
      case 2 => ("E",Position(0,random.nextInt(10)))
      case 3 => ("W",Position(9,random.nextInt(10)))
    }
  }

  // Functions to ask user for inputs

  /**
    * Prompt the user for play mode
    * @return mode
    */
  def getGameModeFromUserInput(): Int = {
    try {
      println("Press the number that corresponds to the desired play mode :\n1 - Multi-player\n2 - VS Easy AI\n3 - VS Medium AI\n4 - VS Difficult AI\nmode : ")
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
    * @return user name
    */
  def getUserNameFromInput(): String = {
    val userNameInput = readLine("Enter your player name, please : ")
    if (userNameInput.isEmpty) {
      println("No name was written. Please try again")
      getUserNameFromInput()
    }
    else userNameInput
  }

  /**
    * handle an input position from user
    * @param inputPos string input by user
    * @return position
    */
  def handlePositionInput(inputPos : String) : Position = {
    val inputArray = inputPos.splitAt(1)
    val inputX = inputArray._1.toUpperCase().toCharArray.head.toInt
    val inputPosX = inputX - mod + 1
    val inputPosY : Int = inputArray._2.toInt
    Position(inputPosX - 1,inputPosY - 1)
  }
  /**
    * Ask for positions for a ship
    * @param size of the ship to place
    * @return list of position of the ship
    */
  def getPositionsShipFromInput(size:Int): List[Position] = {
    if (size == 0)
      Nil
    else {
      val inputPos = readLine("Position : ")
      try {
        val position = handlePositionInput(inputPos)
        if (!position.isValidPosition()) {
          println("Not a valid position (out of the grid). Please Try again")
          getPositionsShipFromInput(size)
        } else {
          position::getPositionsShipFromInput(size - 1)
        }
      } catch {
        case e:Exception =>
          println("A problem occurred. Make sure you wrote the position like the example.")
          getPositionsShipFromInput(size)
      }
    }
  }

  /**
    * Ask the direction of the ship
    * @param boatType (String name, size int) type of shi to place
    * @return the direction of the ship
    */
  def getDirectionShipFromInput(boatType: BoatType): String = {
    val input = readLine("Direction : ")
    val inputUp = input.toUpperCase
    inputUp match {
      case "N" => inputUp
      case "S" => inputUp
      case "E" => inputUp
      case "W" => inputUp
      case _ =>
        println("This is not a valid direction. Please Try again\n")
        getDirectionShipFromInput(boatType)
    }
  }

  /**
    * Check if a list of positions is next to each other
    * @param positions list of Position where to place ship
    * @param direction string to indicate where the ship is "going"
    * @return true if position next to each other, false or else
    */
  def isValidPositionList(positions :List[Position], direction : String): Boolean = {
    if (positions.size == 1)
      true
    else {
      val pos1 = positions.head
      val positionsChanged = positions.tail
      val pos2 = positionsChanged.head
      direction match {
        case `direction` if direction == "N" || direction == "S" =>
          val diff = Math.abs(pos1.x - pos2.x)
          if (pos1.y != pos2.y || diff != 1)
            false
          else
            isValidPositionList(positionsChanged, direction)
        case `direction` if direction == "E" || direction == "W" =>
          val diff = Math.abs(pos1.y - pos2.y)
          if (pos1.x != pos2.x || diff != 1)
            false
          else
            isValidPositionList(positionsChanged, direction)
      }
    }
  }

  /**
    * Ask to place the ships
    * @param boatType (String name, size int) type of shi to place
    * @return (direction, positionsOfShip)
    */
  def getPositionForShipPlacing(boatType: BoatType): (String,List[Position]) = {
    println(s"Place your ${boatType.name} of size ${boatType.size} :")
    val validDirection = getDirectionShipFromInput(boatType)
    val positionsInput = getPositionsShipFromInput(boatType.size)
    if (isValidPositionList(positionsInput,validDirection)) {
      (validDirection,positionsInput)
    } else {
      println("The positions for the ship are not in the right configuration, please try again")
      getPositionForShipPlacing(boatType)
    }
  }

  /**
    * ask for target to shoot
    * @return Position which is the target of the shot
    */
  def getTargetFromInput() : Position = {
    val inputPos = readLine("Target : ")
    try {
      val position = handlePositionInput(inputPos)
      if (!position.isValidPosition()) {
        println("Not a valid target (out of the grid). Please Try again")
        getTargetFromInput()
      } else {
        position
      }
    } catch {
      case e: Exception =>
        println("A problem occurred. Make sure you wrote the position like the example.")
        getTargetFromInput()
    }
  }

  // functions to display message on the console

  /**
    * Indicates what is the game
    */
  def displayTitle() : Unit = {
    println("Hello ! You are going to play a battleship !")
  }
  /**
    * Indicates which mode was selected
    * @param mode
    */
  def displayModeSelectionChosen(mode : Int) : Unit = {
    mode match {
      case 1 => println("You chose to play with another Player")
      case 2 => println("You chose to play against the Easy AI")
      case 3 => println("You chose to play against the Medium AI")
      case 4 => println("You chose to play against the Difficult AI")
      case _ => println("That's not a possibility. Try again.")
    }
  }
  /**
    * Gives guidelines before placing the ships
    * @param name
    */
  def displayBeforePlacingShip(name: String): Unit = {
    println(s"Player $name. It's your turn !\n")
    println("You are going to place your ships.\n")

    println("The grid for this battleship goes from A to J on the horizontal axe and 1 and 10 on the vertical axe.")
    println("For each ship, a direction will be asked, Please Answer by N (NORTH), S (SOUTH), E (EAST) or W (WEST).")
  }

  /**
    * Gives guidelines before starting to play
    */
  def displayBeforePlaying(): Unit = {
    println("Let's Play")
    println("To do so, a target will be asked each turn.\nPlease answer like the example: A1")
  }

  /**
    * Indicates to the player that it is his/her turn to play
    * @param name
    */
  def displayPlayerTurn(name : String) : Unit = {
    println(s"\nPlayer $name. It's your turn !\n")
  }

  /**
    * change of turn
    */
  def displayChangeTurn() : Unit = {
    println("And now the next player")
  }

  /**
    * The AI is placing its ships
    */
  def displayWaitForAI() : Unit = {
    println("Please wait. The AI is placing its ships...\n")
  }

  /**
    * The AI turn to play
    */
  def displayAITurn() : Unit = {
    println(s"\nIt's the AI turn !\n")
  }

  /**
    * Clear the console so the next player does not see
    */
  def clearConsole() : Unit = {
    print("\033[H\033[2J")
  }

  /**
    * Indicates to player when he/whe chose the same place for different ships
    */
  def displayPlaceOccupied(): Unit = println("Those positions are already occupied by ships. Please choose differently.")

  /**
    * Indicates the end of game and the name of the winner
    * @param g gamestate
    */
  def displayWinner(g:GameState) : Unit = {
    val winner = g.getOpponent.name
    println(s"Player $winner Congratulations You Won !")
  }

  /**
    * Shows hit message
    */
  def displayHit(): Unit = {
    println("Target Hit")
  }

  /**
    * Shows missed message
    */
  def displayMissed(): Unit = {
    println("Target Missed")
  }

  /**
    * Shows sunk message
    */
  def displaySunk(): Unit = {
    println("Target Sunk")
  }

  /**
    * Shows already tried message
    */
  def displayTried(): Unit = {
    println("You already tried to shoot this case")
  }

  /**
    * Ask the player if he/she wants to replay
    * @return the answer of the player
    */
  def askForRematch() : String = {
    val input = readLine("Do you want to replay or quit the game ?\n- P : Play again\n- Q : Quit battleship\n").toUpperCase
    input match {
      case "P" => "replay"
      case "Q" => "quit"
      case _ =>
        println("This is not a valid option. Please Try again")
        askForRematch()
    }
  }

}
