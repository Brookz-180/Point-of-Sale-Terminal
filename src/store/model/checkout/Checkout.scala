package store.model.checkout

class Checkout(sc: SelfCheckout) extends State(sc) {
  override def cashPressed(): Unit = {
    this.sc.cart = List()
    this.sc.barcodeEntered = ""
    for ((key, value) <- this.sc.itemMap) {
      value.i = 0
    }
    this.sc.state = new Startup(this.sc)
  }

  override def creditPressed(): Unit = {
    this.sc.cart = List()
    this.sc.barcodeEntered = ""
    for ((key, value) <- this.sc.itemMap) {
      value.i = 0
    }
    this.sc.state = new Startup(this.sc)
  }

  override def displayString(): String = {
    this.sc.barcodeEntered = "cash or credit"
    this.sc.barcodeEntered.toString
  }

  override def loyaltyCardPressed(): Unit = {
    for ((key, value) <- this.sc.itemMap) {
      value.i = 1
    }
    for (item <- this.sc.cart) {
      item.price()
    }
    this.sc.subtotal()
    this.sc.tax()
    this.sc.total()
  }
}
