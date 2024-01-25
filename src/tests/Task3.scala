package tests

import org.scalatest.FunSuite
import store.model.checkout.SelfCheckout
import store.model.items._

class Task3 extends FunSuite {
  test("test 1 - rescan by pressing enter") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor", 500.0)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123", monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.enterPressed()
    testSC.enterPressed()
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart(2).description() == "laptop")
  }

  test("test 2 - no item scanned yet or pressing clear no longer scans the last item") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    testSC.addItemToStore("12", laptop)
    testSC.enterPressed()
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    assert(testSC.displayString() == "")
    testSC.enterPressed()
    testSC.clearPressed()
    testSC.enterPressed()
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart(0).description() == "error")
    assert(cart(3).description() == "error")
  }

  test("test 3 - cash,credit,loyalty buttons don't prevent rescan and don't work until checkout is pressed") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    testSC.addItemToStore("12", laptop)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.cashPressed()
    assert(testSC.displayString() == "12")
    testSC.enterPressed()
    testSC.cashPressed()
    testSC.creditPressed()
    testSC.cashPressed()
    testSC.loyaltyCardPressed()
    testSC.enterPressed()
    testSC.enterPressed()
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart.length == 3)
    assert(cart(2).description() == "laptop")
  }

  test("test 4 - checking out functions") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    testSC.addItemToStore("12", laptop)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.checkoutPressed()
    var cart: List[Item] = testSC.itemsInCart()
    assert(testSC.displayString() == "cash or credit")
    testSC.numberPressed(1)
    testSC.enterPressed()
    testSC.clearPressed()
    assert(testSC.displayString() == "cash or credit")
    testSC.cashPressed()
    assert(testSC.displayString() == "")
    cart = testSC.itemsInCart()
    assert(cart.length == 0)
    testSC.enterPressed()
    cart = testSC.itemsInCart()
    assert(cart(0).description() == "error")
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.checkoutPressed()
    testSC.creditPressed()
    cart = testSC.itemsInCart()
    assert(testSC.displayString() == "")
    assert(cart.length == 0)
  }
}
