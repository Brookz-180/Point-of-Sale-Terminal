package store.model.checkout

class Duplicate(sc: SelfCheckout) extends State(sc){
  override def numberPressed(number: Int): Unit = {
    this.sc.barcodeEntered += number.toString
    this.sc.state = new Scanning(this.sc)
  }

  override def clearPressed(): Unit = {
    this.sc.barcodeEntered = ""
    this.sc.state = new Startup(this.sc)
  }

  override def enterPressed(): Unit = {
    this.sc.cart = this.sc.cart :+ this.sc.cart.last
    this.sc.barcodeEntered = ""
    //this.sc.state = new Duplicate(this.sc)
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

  override def checkoutPressed(): Unit = {
    this.sc.state = new Checkout(this.sc)
  }
}
