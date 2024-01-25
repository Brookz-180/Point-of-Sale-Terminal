package store.model.items

import store.model.items.Modifier

class Item(var des: String, var bp: Double) {

  var p: Double = bp // the p variable will represent the current price so bp doesn't change with modifiers
  var modifiers: List[Modifier] = List()
  var tx: Double = 0.0
  var i: Int = 0 //controls if loyalty sale is on. 0 is off, 1 is on
  def description(): String = {
    this.des
  }

  def setBasePrice(basePrice: Double): Unit = {
    this.bp = basePrice
  }

  def price(): Double = {
    this.p = this.bp
    for (m <- modifiers) {
      m.i = this.i
      this.p = m.updatePrice(this.p)
    }
    this.p
  }

  def tax(): Double = {
    this.tx = 0.0
    this.p = price()
    for (m <- modifiers) {
      this.tx = this.tx + m.computeTax(this.p)
    }
    this.tx
  }

  def addModifier(mod: Modifier): Unit = {
    this.modifiers = this.modifiers :+ mod
  }

}
