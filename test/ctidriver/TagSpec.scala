/**
  * Copyright (c) 2016 Naoto Shimazaki
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
  * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
  * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
  * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all copies or substantial
  * portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
  * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
  * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
  * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
  * DEALINGS IN THE SOFTWARE.
  */

package ctidriver

import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner


/**
  * Created by nshimaza on 2016/01/08.
  */
@RunWith(classOf[JUnitRunner])
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
    assert(WrapupDataOutgoingMode.id == 614)
  }

  test("ZZZZInternalUseOnly must be 615") {
    assert(ZZZZInternalUseOnly.id == 615)
  }
}
