package ship

case class Ship(name: String, size: Int, direction: String, positions: List[Position]) {

  def isSunk(): Boolean = if (size == 0) true else false
}
