package store.model.checkout

import store.model.items._

class SelfCheckout {
  var barcodeEntered: String = ""
  var itemMap: Map[String,Item] = Map()
  var cart: List[Item] = List()
  val errorItem: Item = new Item("error",0.0)
  var subtot: Double = 0.0
  var tx: Double = 0.0
  var tot: Double = 0.0

  var state: State = new Startup(this)

  def addItemToStore(barcode: String, item: Item): Unit = {
    // This method adds an item to your store's checkout system. It does not add an item to the customer's cart
    this.itemMap += (barcode -> item)
  }

  def numberPressed(number: Int): Unit = {
    //this.barcodeEntered += number.toString
    this.state.numberPressed(number)
  }

  def clearPressed(): Unit = {
    //this.barcodeEntered = ""
    this.state.clearPressed()
  }

  def enterPressed(): Unit = {
    //this.cart = this.cart :+ this.itemMap.getOrElse(barcodeEntered, errorItem)
    //this.barcodeEntered = ""
    this.state.enterPressed()
  }

  def checkoutPressed(): Unit = {
    this.state.checkoutPressed()
  }

  def cashPressed(): Unit = {
    this.state.cashPressed()
  }

  def creditPressed(): Unit = {
    this.state.creditPressed()
  }

  def loyaltyCardPressed(): Unit = {
    this.state.loyaltyCardPressed()
  }

  def displayString(): String = {
    //barcodeEntered.toString
    this.state.displayString()
  }

  def itemsInCart(): List[Item] = {
    this.cart
  }

  def subtotal(): Double = {
    this.subtot = 0.0
    for (item <- this.cart) {
      this.subtot = this.subtot + item.price()
    }
    this.subtot
  }

  def tax(): Double = {
    this.tx = 0.0
    for (item <- this.cart) {
      this.tx = this.tx + item.tax()
    }
    this.tx
  }

  def total(): Double = {
    this.tot = 0.0
    this.tot = subtotal() + tax()
    //this.tot = this.tot + this.subtot + this.tx
    this.tot
  }

  def prepareStore(): Unit = {
    // Similar to openMap in the Pale Blue Dot assignment, this method is not required and is
    // meant to help you run manual tests.
    //
    // This method is called by the GUI during setup. Use this method to prepare your
    // items and call addItemToStore to add their barcodes. Also add any sales/tax/etc to your
    // items.
    //
    // This method will not be called during testing and you should not call it in your tests.
    // Each test must setup its own items to ensure compatibility in AutoLab. However, you can
    // write a similar method in your Test Suite classes.

    // Example usage:
    val testItem: Item = new Item("test item", 100.0)
    val testItem2: Item = new Item("test item 2", 100.0)
    var ls1: LoyaltySale = new LoyaltySale(50.0)
    //var sale1: Sale = new Sale(10.0)
    //var tax1: SalesTax = new SalesTax(10.0)
    //var bd1: BottleDeposit = new BottleDeposit(0.05)
    testItem.addModifier(ls1)
    //testItem.addModifier(tax1)
    //testItem.addModifier(bd1)
    //testItem.addModifier(sale1)
    this.addItemToStore("472", testItem)
    this.addItemToStore("8", testItem2)
  }

}
