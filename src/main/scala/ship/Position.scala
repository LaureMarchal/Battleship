package ship

case class Position(x:Int,y:Int) {

  /**
    * Check if a position is on the grid
    * @return
    */
  def isValidPosition():Boolean = {
    if ((x < 0 || x > 9) && (y < 0 || y > 9)) false
    else true
  }
}
