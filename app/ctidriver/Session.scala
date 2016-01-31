package ctidriver

import java.net.InetSocketAddress

import akka.actor.{ Actor, ActorRef, Props }
import akka.io.{ IO, Tcp }
import akka.util.ByteString

/**
  * Use this class instance only within an Actor.
  * This code is not thread safe.  Never call single instance from multiple threads.
  */
class Packetizer(packet_listener: ActorRef) {

  object State extends Enumeration {
    val WAIT_LENGTH, WAIT_BODY = Value
  }

  var buf = ByteString.empty
  var state = State.WAIT_LENGTH
  var offset = -4

  def receive(data: ByteString) = {
    offset = offset + data.size
    buf = buf ++ data

    while (offset >= 0) {
      state match {
        case State.WAIT_LENGTH =>
          // at the state WAIT_LENGTH, the head of buf is aligned to message length field
          // now you have plenty bytes to decode message length
          val message_length = buf.toInt
          buf = buf.drop(4)
          // here you have buf which contains message aligned to Message Type field at the head
          // let's set next state for waiting body
          state = State.WAIT_BODY

          // the message length doesn't include Message Type filed so you need to wait 4 more bytes
          offset = buf.size - (message_length + 4)

        case State.WAIT_BODY =>
          // at the state WAIT_BODY, the head of buf is aligned to message type field
          // now you have at least one entire message in buf

          // take single message to packet
          val len = buf.size - offset
          val packet = buf.take(len)

          buf = buf.drop(len)
          // here you have buf which is aligned to next message length
          // let's set next state for waiting message length
          // we need 4 bytes to continue
          state = State.WAIT_LENGTH
          offset = buf.size - 4

          packet_listener ! packet
      }
    }
  }
}

object SessionProtocol {
  case object Close
}

class SocketActor(cti_server: InetSocketAddress, listener: ActorRef) extends Actor {
  import akka.io.Tcp._
  import context.system

  val packetizer = new Packetizer(listener)

  IO(Tcp) ! Connect(cti_server)

  def receive = {
    case CommandFailed(_: Connect) =>
      println("connect to cti server failed.")
      context stop self

    case c @ Connected(remote, local) =>
      val connection = sender()
      connection ! Register(self)
      context become {
        case Received(data) =>
          packetizer receive data

        case _: ConnectionClosed =>
          context stop self

        case SessionProtocol.Close =>
          connection ! Tcp.Close
      }
      listener ! c

  }

}




/**
  * Packetizing binary stream from TCP into entire CTI Server Protocol message.
  */
class PacketizerActor(packet_listener: ActorRef) extends Actor {

  object State extends Enumeration {
    val WAIT_LENGTH, WAIT_BODY = Value
  }

  var buf = ByteString.empty
  var state = State.WAIT_LENGTH
  var offset = -4

  def receive = {
    case data: ByteString =>
      offset = offset + data.size
      buf = buf ++ data

      while (offset >= 0) {
        state match {
          case State.WAIT_LENGTH =>
            // at the state WAIT_LENGTH, the head of buf is aligned to message length field
            // now you have plenty bytes to decode message length
            val message_length = buf.toInt
            buf = buf.drop(4)
            // here you have buf which contains message aligned to Message Type field at the head
            // let's set next state for waiting body
            state = State.WAIT_BODY

            // the message length doesn't include Message Type filed so you need to wait 4 more bytes
            offset = buf.size - (message_length + 4)

          case State.WAIT_BODY =>
            // at the state WAIT_BODY, the head of buf is aligned to message type field
            // now you have at least one entire message in buf

            // take single message to packet
            val len = buf.size - offset
            val packet = buf.take(len)

            buf = buf.drop(len)
            // here you have buf which is aligned to next message length
            // let's set next state for waiting message length
            // we need 4 bytes to continue
            state = State.WAIT_LENGTH
            offset = buf.size - 4

            packet_listener ! packet
        }
      }
  }
}

