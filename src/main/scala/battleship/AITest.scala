package battleship

//import java.io.{BufferedWriter, File, FileWriter}

import battleship.AIs.EasyAI
import battleship.Game._

import scala.annotation.tailrec

object AITest extends App{

  def clear(p: Player) : Player = {
    val pToUpdate = p
    if (p.isInstanceOf[EasyAI]) {
      val newP = createPlayer(1)
      newP.score = pToUpdate.score
      newP
    }
    else {
      val newP = createPlayer(2)
      newP.score = pToUpdate.score
      newP
    }
  }

  def easyVsMedium() : GameState = {
    val player1 = createPlayer(1)
    val player2 = createPlayer(2)
    val gameState = GameState(player1,player2)
    //play 100 times
    play100Times(gameState)
    //writeInCsv(levelEasy,scoreEasy,levelMedium,scoreMedium)
  }

  def easyVsDifficult() : Unit = {
    val player1 = createPlayer(1)
    val player2 = createPlayer(3)
    var gameState = GameState(player1,player2)
    //play 100 times
    //val (levelEasy,scoreEasy,levelDifficult,scoreDiff) = play100Times(gameState)
    //writeInCsv(levelEasy,scoreEasy,levelDifficult,scoreDiff)
  }

  def mediumVsDifficult() : Unit = {
    val player1 = createPlayer(2)
    val player2 = createPlayer(3)
    var gameState = GameState(player1,player2)
    //play 100 times
    //val (levelMedium,scoreMedium,levelDifficult,scoreDiff) = play100Times(gameState)
    //writeInCsv(levelMedium,scoreMedium,levelDifficult,scoreDiff)
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
        //play
        gameStateRunning = placeShipsLoop(gameState)
        gameStateRunning = gameLoop(gameStateRunning)
        //winner add 1 to score
        gameStateRunning.getOpponent.score += 1
        println("score : ")
        print(gameStateRunning.getOpponent.score)
        //switch starter
        if (startingAI.equals(gameStateRunning.getActivePlayer)) play100TimesRec(GameState(gameStateRunning.getOpponent,gameStateRunning.getActivePlayer))
        else play100TimesRec(GameState(gameStateRunning.getActivePlayer,gameStateRunning.getOpponent))
      }
    }
    play100TimesRec(gameState)
  }
  /*
    def writeInCsv(level1: Int, score1: Int, level2: Int, score2: Int): Unit = {

    }

    def writeInCsv() : Unit = {
      val header = "AI Name; score; AI Name2; score2\n"
      var scores = ""
      scores += "AI Level Beginner; X1; Level Medium; Y1\n"
      scores += "AI Level Beginner;X2;Level Hard;Y2\n"
      scores += "AI Medium;X3;Level Hard;Y3\n"

      // File writing
      val file = new File("../../../ai_proof.csv")
      val bw = new BufferedWriter(new FileWriter(file))
      bw.write(header.concat(scores))
      bw.close()
      println("The file `ai_proof.csv` was successfully created.")
    }*/

  def execute() : Unit = {
    val newGameState = easyVsMedium()
    println("Final Scores :\n")
    print(newGameState.getActivePlayer)
    print(" => ")
    print(newGameState.getActivePlayer.score)
    print("\n")
    print(newGameState.getOpponent)
    print(" => ")
    print(newGameState.getOpponent.score)
  }

  execute()

}
