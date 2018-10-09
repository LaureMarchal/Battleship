package battleship

import java.io.{BufferedWriter, File, FileWriter}

import battleship.AIs.{DifficultAI, EasyAI, MediumAI}
import battleship.Game._
import battleship.helpers.CaseType
import battleship.helpers.Helper._

import scala.annotation.tailrec

/**
  * Test AI vs AI
  */
object AITest {

  def placeShips(g: GameState) : Unit = {
    val listShips1 = g.getActivePlayer.placeShips(shipsType)
    g.getActivePlayer.ships = listShips1
    val listShips2 = g.getOpponent.placeShips(shipsType)
    g.getOpponent.ships = listShips2
  }

  def clear(p: Player) : Player = {
    p match {
      case _: EasyAI =>
        val player = EasyAI(Grid(List.fill(10)(List.fill(10)(CaseType.W))), Grid(List.fill(10)(List.fill(10)(CaseType.W))),getLivePoints(shipsType))
        player.score = p.score
        player
      case _: MediumAI =>
        val player = MediumAI(Grid(List.fill(10)(List.fill(10)(CaseType.W))), Grid(List.fill(10)(List.fill(10)(CaseType.W))), getLivePoints(shipsType))
        player.score = p.score
        player
      case _: DifficultAI =>
        val player = DifficultAI(Grid(List.fill(10)(List.fill(10)(CaseType.W))), Grid(List.fill(10)(List.fill(10)(CaseType.W))), getLivePoints(shipsType))
        player.score = p.score
        player
    }
  }

  def easyVsMedium() : GameState = {
    val player1 = createPlayer(1)
    val player2 = createPlayer(2)
    val gameState = GameState(player1,player2)
    //play 100 times
    play100Times(gameState)
  }

  def easyVsDifficult() : GameState = {
    val player1 = createPlayer(1)
    val player2 = createPlayer(3)
    var gameState = GameState(player1,player2)
    ///play 100 times
    play100Times(gameState)
  }

  def mediumVsDifficult() : GameState = {
    val player1 = createPlayer(2)
    val player2 = createPlayer(3)
    var gameState = GameState(player1,player2)
    //play 100 times
    play100Times(gameState)
  }

  def play100Times(gameState : GameState): GameState = {
    @tailrec
    def play100TimesRec(gameState : GameState) : GameState = {
      if (gameState.getOpponent.score + gameState.getActivePlayer.score == 100)
        gameState
      else {
        val startingAI = gameState.getActivePlayer
        // clear the grids and ships before playing a new game
        var gameStateRunning = GameState(clear(gameState.getActivePlayer),clear(gameState.getOpponent))
        //var gameStateRunning = GameState(clear(gameState.getActivePlayer),clear(gameState.getOpponent))
        //play
        placeShips(gameStateRunning)
        @tailrec
        def game(g:GameState): GameState = {
          if (g.getActivePlayer.livePoints == 0)
            g
          else {
            // Ask/get target
            val target = g.getActivePlayer.chooseTarget()
            // Shoot
            g.getActivePlayer.shoot(target,g.getOpponent)
            //Clear the console for next player
            clearConsole()
            //Switch Players
            game(g.switchPlayers)
          }
        }
        gameStateRunning = game(gameStateRunning)

        //winner add 1 to score
        gameStateRunning.getOpponent.score += 1
        //switch starter
        if (startingAI.equals(gameStateRunning.getActivePlayer)) {
          play100TimesRec(GameState(gameStateRunning.getOpponent,gameStateRunning.getActivePlayer))
        }
        else {
          play100TimesRec(GameState(gameStateRunning.getActivePlayer,gameStateRunning.getOpponent))
        }
      }
    }
    play100TimesRec(gameState)
  }


    def writeInCsv(states: List[GameState]) : Unit = {
      val header = "AI Name; score; AI Name2; score2\n"
      var scores = ""
      val game1 = states.head
      val newStates = states.tail
      val game2 = newStates.head
      val newStates2 = newStates.tail
      val game3 = newStates2.head
      val scoreline1 = game1.getActivePlayer.name + "; " + game1.getActivePlayer.score + "; "+ game1.getOpponent.name+"; " + game1.getOpponent.score+"\n"
      val scoreline2 = game2.getActivePlayer.name + "; " + game2.getActivePlayer.score + "; "+ game2.getOpponent.name+"; " + game2.getOpponent.score+"\n"
      val scoreline3 = game3.getActivePlayer.name + "; " + game3.getActivePlayer.score + "; "+ game3.getOpponent.name+"; " + game3.getOpponent.score+"\n"
      scores += scoreline1
      scores += scoreline2
      scores += scoreline3

      // File writing
      val file = new File("ai_proof.csv")
      val bw = new BufferedWriter(new FileWriter(file))
      bw.write(header.concat(scores))
      bw.close()
      println("The file `ai_proof.csv` was successfully created.")
    }

  def execute() : Unit = {
    val state1 = easyVsMedium()
    val state2 = easyVsDifficult()
    val state3 = mediumVsDifficult()
    val test = List(state1,state2,state3)
    writeInCsv(test)
  }

}
