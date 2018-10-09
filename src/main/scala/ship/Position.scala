package ship

/**
  * Position (int x, int y)
  * @param x int for horizontal axe
  * @param y int for vertical axe
  */
case class Position(y:Int,x:Int) {

  /**
    * Check if a position is on the grid
    * @return true if position in the grid or else false
    */
  def isValidPosition():Boolean = x > -1 && x < 10 && y > -1 && y < 10

  /**
    * true if the coordinates is on the min limit of the grid or else false
    * @return
    */
  def isLimitPositionMin(coordinate: Int):Boolean = coordinate == 0

  /**
    * true if the coordinates is on the min limit of the grid or else false
    * @return
    */
  def isLimitPositionMax(coordinate: Int):Boolean = coordinate == 9
}
