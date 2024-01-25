package store.model.items

class BottleDeposit(var dep: Double) extends Modifier() {

  override def updatePrice(op: Double): Double = {
    op
  }

  override def computeTax(price: Double): Double = {
    this.dep
  }
}
