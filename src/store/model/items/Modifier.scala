package store.model.items

abstract class Modifier() {
  var i: Int = 0 //going to be the same as the i in the item class
  def updatePrice(op: Double): Double //op is old price

  def computeTax(price: Double): Double

}
