package ctidriver

/**
  * Created by nshimaza on 2016/01/21.
  */
class FakeCtiServer(var scenario: Seq[Message]) {

  def tick = {
    // encode first message in rest
    scenario = scenario.tail
    // send encoded byte string to client
  }
}
