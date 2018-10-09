package battleship.helpers

/**
  * CaseType Enumeration to define the case possibilities
  */
object CaseType extends Enumeration {
  type CaseType = Value
  /**
    * Constants for case in Grid :
    *
    * W => WATER : the case contains water
    * S => SHIP : the case contains a ship position
    * H => HIT : the case used to contain a ship position but was shot
    * M => MISSED : the case used to contain water but was shot
    *
    * Constants used to return particular results :
    *
    * Sunk : the case HIT was the last position of a ship
    * Tried : the case has been shot (HIT or MISSED)
    */
  val W, S, H, M, Sunk, Tried = Value
}
