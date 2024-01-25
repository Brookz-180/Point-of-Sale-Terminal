package tests

import org.scalatest.FunSuite
import store.model.checkout.SelfCheckout
import store.model.items.Item

class Task1 extends FunSuite {
// need to call methods to test things since autolab doesn't have the same names
  // ex. to test description of item, use assert(testItem.description() == "test item")
  //testSelfCheckout.addItemToStore("123",testItem)
  //testSelfCheckout.numberPressed(1)
  // same 2 and 3 and enter
  //val cart: List[Item] = testSelfCheckout.itemsInCart()
  //assert(cart.length == 1)
  //assert(cart.head.description() == "test item")
  test("Basic Functions") {
//    var testSelfCheckout: SelfCheckout = new SelfCheckout()
//    var testItem: Item = new Item("test item", 100.0)
//    testSelfCheckout.addItemToStore("123", testItem)
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    //check description method
    assert(laptop.description() == "laptop")
    //check price method
    assert(Math.abs(laptop.price() - 1000.0) < 0.001)
    //test set base price method
    laptop.setBasePrice(500.0)
    assert(Math.abs(laptop.price() - 500.0) < 0.001)
    //test add item to store, number pressed, enter pressed, items in cart
    testSC.addItemToStore("12", laptop)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart.length == 1)
    assert(cart.head.description() == "laptop")
    assert(Math.abs(cart.head.price() - 500.0) < 0.001)
  }
  test("Clear Method") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    testSC.addItemToStore("12",laptop)
    testSC.numberPressed(1)
    testSC.clearPressed()
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart.head.description() == "laptop")
    assert(Math.abs(cart.head.price() - 1000.0) < 0.001)
  }
  test("Error Item") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    testSC.addItemToStore("12", laptop)
    testSC.numberPressed(1)
    testSC.enterPressed()
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart.head.description() == "error")
    assert(Math.abs(cart.head.price() - 0.0) < 0.001)
  }
  test("Doesn't Initially Display Empty String, Always Adds the Same Item, Receipt Order Reversed") {
    var testSC: SelfCheckout = new SelfCheckout()
    assert(testSC.displayString() == "")
    var laptop: Item = new Item("laptop", 1000.0)
    var desktop: Item = new Item("desktop", 2000.0)
    testSC.addItemToStore("12", laptop)
    testSC.addItemToStore("2", desktop)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    testSC.numberPressed(2)
    testSC.enterPressed()
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart.head.description() != cart(1).description())
    assert(cart.head.description() == "laptop")
    assert(cart(1).description() == "desktop")
  }
  test("Price Change Doesn't Update Items In Cart") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    testSC.addItemToStore("12", laptop)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    testSC.enterPressed()
    laptop.setBasePrice(500.0)
    val cart: List[Item] = testSC.itemsInCart()
    assert(Math.abs(cart.head.price() - 500.0) < 0.001)
  }
  test("Doesn't Accept Leading Zeroes") {
    var testSC: SelfCheckout = new SelfCheckout()
    var laptop: Item = new Item("laptop", 1000.0)
    testSC.addItemToStore("00012", laptop)
    testSC.numberPressed(0)
    testSC.numberPressed(0)
    testSC.numberPressed(0)
    testSC.numberPressed(1)
    testSC.numberPressed(2)
    assert(testSC.displayString() == "00012")
    testSC.enterPressed()
    //the 2 lines below are causing the test to fail Jesse's code
    val cart: List[Item] = testSC.itemsInCart()
    assert(cart.head.description() == "laptop")
   }
}
