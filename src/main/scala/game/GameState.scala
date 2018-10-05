package game

import player.Player

case class GameState(active: Player, opponent:Player) {

  def getActivePlayer: Player = active

  def getOpponent: Player = opponent

  def switchPlayers: GameState = GameState(opponent, active)
}
