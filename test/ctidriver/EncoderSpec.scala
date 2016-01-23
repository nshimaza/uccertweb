import akka.util.ByteString
import ctidriver.MessageType._
import ctidriver.Tag._
import ctidriver._
import org.scalatest.FunSuite
import scala.collection.immutable.BitSet


/**
 * Created by x on 2014/06/29.
 */
class EncoderSpec extends FunSuite {

  test("encoding 000: UNKNOWN_TYPE") {
    val raw = ByteString(0,0,0,0, 1,2,3,4,5,6,7,8,9,10)
    val msg: Message = List((MessageTypeTag, Some(UNKNOWN_TYPE)), (RawBytes, ByteString(1,2,3,4,5,6,7,8,9,10)))

    assert(Encoder.encode(msg) == raw)
  }





  test("encoding OPEN_REQ") {
    val src = Encoder.buildCtiRequest(Encoder.buildOpenReq(
      serviceRequested = BitSet.empty + CtiServiceMask.ALL_EVENTS,
      callMsgMask = BitSet.empty + CallEventMessageMask.BEGIN_CALL,
      agentStateMask = BitSet.empty + AgentStateMask.AGENT_TALKING,
      configMsgMask = 0,
      clientID = "ClientID",
      clientPassword = "ClientPassword"), 10)
    val msg = ByteString(0,0,0,72, 0,0,0,3, 0,0,0,10, 0,0,0,19, 0xff,0xff,0xff,0xff, 0,0,0x13,0x88,
      0,0,0,0x10, 0,0,0x20,0, 0,0,0,0x10, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
      1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
      2,15,0x43,0x6c,0x69,0x65,0x6e,0x74,0x50,0x61,0x73,0x73,0x77,0x6f,0x72,0x64,0
    )

    assert(src == msg)
  }

}
