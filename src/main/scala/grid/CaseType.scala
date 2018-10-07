package grid

/**
  * CaseType Enumeration to define the case possibilities
  */
object CaseType extends Enumeration {
  type CaseType = Value
  val W, S, H, M, Sunk, Tried = Value
}
