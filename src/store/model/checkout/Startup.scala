package store.model.checkout
//startup happens when SC first starts or after clear is pressed during scanning
class Startup(sc: SelfCheckout) extends State(sc) {
  override def numberPressed(number: Int): Unit = {
    this.sc.barcodeEntered += number.toString
    this.sc.state = new Scanning(this.sc)
  }

  override def clearPressed(): Unit = {
    this.sc.barcodeEntered = ""
  }

  override def enterPressed(): Unit = {
    this.sc.cart = this.sc.cart :+ this.sc.errorItem
    this.sc.barcodeEntered = ""
  }

  override def checkoutPressed(): Unit = {
    this.sc.state = new Checkout(this.sc)
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
