package tests

import org.scalatest.FunSuite
import store.model.checkout.SelfCheckout
import store.model.items._ //the underscore imports everything in the items package

class Task2 extends FunSuite{
  //doubles testing helper method
  val epsilon = 0.001
  def compareDoubles(d1: Double, d2:Double):Boolean ={
    return Math.abs(d1-d2) < epsilon
  }
// in class example
//  test("test multiple sales") {
//    val item: Item = new Item("testing", 100.0)
//    val sale1: Sale = new Sale(20.0)
//    val sale2: Modifier = new Sale(10.0) //can be type Sale or Modifier
//    item.addModifier(sale1)
//    item.addModifier(sale2)
//    assert(compareDoubles(item.price(),72.0)
//    assert(compareDoubles(item.tax(),0.0))
//  }

  test("test 1 - sale") {
    var laptop: Item = new Item("laptop",1000.0)
    val sale1: Sale = new Sale(20.0)
    laptop.addModifier(sale1)
    assert(compareDoubles(laptop.price(),800.0))
  }

  test("tax") {
    var laptop: Item = new Item("laptop", 1000.0)
    val sale1: SalesTax = new SalesTax(8.0)
    laptop.addModifier(sale1)
    assert(compareDoubles(laptop.tax(), 80.0))
  }

  test("bottle deposit") {
    var laptop: Item = new Item("laptop", 1000.0)
    val sale1: BottleDeposit = new BottleDeposit(0.05)
    laptop.addModifier(sale1)
    assert(compareDoubles(laptop.tax(), 0.05))
  }

  test("subtotal and total with no modifiers") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor",500.0)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123",monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.numberPressed(3)
    testSC.enterPressed()
    assert(compareDoubles(testSC.subtotal(), 1500.0))
    assert(compareDoubles(testSC.total(), 1500.0))
  }

  test("subtotal and total with sale") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor", 500.0)
    var sale1: Sale = new Sale(20.0)
    laptop.addModifier(sale1)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123", monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.numberPressed(3)
    testSC.enterPressed()
    assert(compareDoubles(testSC.subtotal(), 1300.0))
    assert(compareDoubles(testSC.total(), 1300.0))
    assert(compareDoubles(testSC.tax(), 0.0))
  }

  test("subtotal and total with tax") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor", 500.0)
    var tax1: SalesTax = new SalesTax(8.0)
    laptop.addModifier(tax1)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123", monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.numberPressed(3)
    testSC.enterPressed()
    assert(compareDoubles(testSC.subtotal(), 1500.0))
    assert(compareDoubles(testSC.tax(), 80.0))
    assert(compareDoubles(testSC.total(), 1580.0))
  }

  test("multiple sales and taxes") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    var monitor: Item = new Item("monitor", 500.0)
    var sale1: Sale = new Sale(20.0)
    var sale2: Sale = new Sale(10.0)
    var tax1: SalesTax = new SalesTax(10.0)
    var tax2: SalesTax = new SalesTax(1.0)
    var bd: BottleDeposit = new BottleDeposit(0.05)
    laptop.addModifier(sale1)
    laptop.addModifier(sale2)
    laptop.addModifier(tax1)
    monitor.addModifier(sale2)
    monitor.addModifier(tax1)
    monitor.addModifier(tax2)
    laptop.addModifier(bd)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("123", monitor)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.numberPressed(3)
    testSC.enterPressed()
    assert(compareDoubles(testSC.tax(),121.55),testSC.tax())
    assert(compareDoubles(testSC.subtotal(), 1170.0),testSC.subtotal())
    //assert(compareDoubles(testSC.tax(),121.55),testSC.tax())
    assert(compareDoubles(testSC.total(), 1291.55),testSC.total())
  }
}
