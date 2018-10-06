package ship

case class Position(x:Int,y:Int) {

  /**
    * Check if a position is on the grid
    * @return
    */
  def isValidPosition():Boolean = {
    if ((x < 1 || x > 10) && (y < 1 || y > 10)) false
    else true
  }
}
