package store.model.items

class Sale(var pct: Double) extends Modifier() {

  override def updatePrice(op: Double): Double = {
    var p: Double = op - (op * (this.pct / 100.0))
    p
  }

  override def computeTax(price: Double): Double = {
    0.0
  }



}
