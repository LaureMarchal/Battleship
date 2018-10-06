package ship

case class Position(x:Int,y:Int) {

  def isValidPosition():Boolean = {
    if ((x < 1 || x > 10) && (y < 1 || y > 10)) false
    else true
  }

  def isValidPositionList(positions :List[Position]): Boolean = {
    false
  }
}
