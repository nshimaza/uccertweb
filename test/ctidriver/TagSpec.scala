package ctidriver

import ctidriver.Tag._
import org.scalatest.FunSuite


/**
  * Created by nshimaza on 2016/01/08.
  */
class TagSpec extends FunSuite {

  test("CLIENT_ID must be 1") {
    assert(CLIENT_ID.id == 1)
  }

  test("DIRECTION must be 244") {
    assert(DIRECTION.id == 244)
  }

  test("RESERVED_255 must be 255") {
    assert(RESERVED_255.id == 255)
  }

  test("MessageType must be 256") {
    assert(MessageTypeTag.id == 256)
  }

  test("ActiveConnectionCallID must be 257") {
    assert(ActiveConnectionCallID.id == 257)
  }

  test("WrapupDataOutgoingMode must be 614") {
    assert(WrapupDataOutgoingMode.id == 611)
  }

  test("ZZZZInternalUseOnly must be 615") {
    assert(ZZZZInternalUseOnly.id == 612)
  }
}
