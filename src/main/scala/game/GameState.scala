package game

import player.Player

/**
  * GameState contains the 2 players and define the player who must play and the other
  * @param active player who plays
  * @param opponent player who waits
  */
case class GameState(active: Player, opponent:Player) {

  /**
    * return player who plays
    * @return player active
    */
  def getActivePlayer: Player = active

  /**
    * player who waits
    * @return opponent player
    */
  def getOpponent: Player = opponent

  /**
    * switch the active and opponent players
    * @return the new gamestate
    */
  def switchPlayers: GameState = GameState(opponent, active)
}
