package tests

import org.scalatest.FunSuite
import store.model.checkout.SelfCheckout
import store.model.items._

class ApplicationObjective extends FunSuite{
  val epsilon = 0.001

  def compareDoubles(d1: Double, d2: Double): Boolean = {
    return Math.abs(d1 - d2) < epsilon
  }

  test("test 1 - loyalty functionality") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor", 500.0)
    var ls1: LoyaltySale = new LoyaltySale(50.0)
    laptop.addModifier(ls1)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123", monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    var cart: List[Item] = testSC.itemsInCart()
    assert(compareDoubles(laptop.price(), 1000.0))
    assert(compareDoubles(testSC.tax(), 0.0))
    assert(compareDoubles(testSC.total(), 1000.0))
    testSC.loyaltyCardPressed()
    cart = testSC.itemsInCart()
    assert(compareDoubles(laptop.price(), 500.0))
    assert(compareDoubles(testSC.total(), 500.0))
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    assert(compareDoubles(testSC.total(), 1000.0))
    testSC.checkoutPressed()
    cart = testSC.itemsInCart()
    testSC.cashPressed()
    cart = testSC.itemsInCart()
    assert(cart.length == 0)
    assert(compareDoubles(testSC.total(), 0.0))
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    cart = testSC.itemsInCart()
    assert(compareDoubles(laptop.price(), 1000.0))
    assert(compareDoubles(testSC.total(), 1000.0))
  }

  test("test 2 - can press loyalty after pressing checkout") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor", 500.0)
    var ls1: LoyaltySale = new LoyaltySale(50.0)
    laptop.addModifier(ls1)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123", monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.checkoutPressed()
    testSC.loyaltyCardPressed()
    var cart: List[Item] = testSC.itemsInCart()
    assert(compareDoubles(laptop.price(), 500.0))
    assert(compareDoubles(testSC.total(), 500.0))
  }

  test("test 3 - multiple loyalty sales with different amounts") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor", 500.0)
    var ls1: LoyaltySale = new LoyaltySale(50.0)
    var ls2: LoyaltySale = new LoyaltySale(10.0)
    laptop.addModifier(ls1)
    monitor.addModifier(ls2)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123", monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.numberPressed(3)
    testSC.enterPressed()
    testSC.loyaltyCardPressed()
    var cart: List[Item] = testSC.itemsInCart()
    assert(compareDoubles(laptop.price(),500.0))
    assert(compareDoubles(monitor.price(),450.0))
    assert(compareDoubles(testSC.total(),950.0))
  }

  test("test 4 - multiple loyalty scans breaks") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var ls1: LoyaltySale = new LoyaltySale(50.0)
    laptop.addModifier(ls1)
    testSC.addItemToStore("12", laptop)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.loyaltyCardPressed()
    testSC.loyaltyCardPressed()
    var cart: List[Item] = testSC.itemsInCart()
    assert(compareDoubles(laptop.price(),500.0))
    assert(compareDoubles(testSC.total(),500.0))
  }
}
