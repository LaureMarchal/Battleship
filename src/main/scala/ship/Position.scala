package ship

/**
  * Position (int x, int y)
  * @param x int for horizontal axe
  * @param y int for vertical axe
  */
case class Position(x:Int,y:Int) {

  /**
    * Check if a position is on the grid
    * @return true if position in the grid or else false
    */
  def isValidPosition():Boolean = {
    if (x > -1 && x < 10 && y > -1 && y < 10)
      true
    else
      false
  }
}
