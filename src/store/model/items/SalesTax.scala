package store.model.items

class SalesTax(var pct: Double) extends Modifier() {

  override def updatePrice(op: Double): Double = {
    op
  }

  override def computeTax(price: Double): Double = {
    var tax: Double = price * (this.pct / 100.0)
    tax
  }
}
