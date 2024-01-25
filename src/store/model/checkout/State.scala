package store.model.checkout

import store.model.items._

abstract class State(var sc: SelfCheckout) {
  def numberPressed(number: Int): Unit = {}
  def clearPressed(): Unit = {}
  def enterPressed(): Unit = {}
  def checkoutPressed(): Unit = {}
  def cashPressed(): Unit = {}
  def creditPressed(): Unit = {}
  def loyaltyCardPressed(): Unit = {}
  def displayString(): String = {
    this.sc.barcodeEntered.toString
  }
}
