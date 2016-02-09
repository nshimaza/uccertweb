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

import java.net.InetSocketAddress

import akka.actor.{ Actor, ActorRef, Props }
import akka.io.{ IO, Tcp }
import akka.util.ByteString

/**
  * Use this class instance only within an Actor.
  * This code is not thread safe.  Never call single instance from multiple threads.
  */
class Packetizer(listener: ByteString => Unit) {

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
          if (message_length < 0 || message_length > MaxMessageLen) {
            val msg = s"Message length $message_length out of defined range (0 - $MaxMessageLen) decoded.  Potential socket out of sync."
            ctilog.error(msg)
            throw new java.io.SyncFailedException(msg)
          }
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

          listener(packet)
      }
    }
  }
}

case class MessageFilterEntry(handler: Message => Unit, set: Set[MessageType.MessageType])

case class MessageFilter(filter_conf: Traversable[MessageFilterEntry]) {
  val jump_table: Array[Traversable[(Message) => Unit]] = {
    val flat_table = for (entry <- filter_conf; mtyp <- entry.set) yield (mtyp, Traversable(entry.handler))
    val base_table = MessageType.values.map((_, Traversable[(Message) => Unit]())).toTraversable
    val optimized_table = (flat_table ++ base_table).groupBy(_._1).map(t => (t._1, t._2.flatMap(_._2)))
    optimized_table.map(t => (t._1.id, t._2)).toSeq.sortWith(_._1 < _._1).map(_._2).toArray
  }

  def receive(packet: ByteString): Unit = {
    val ((tag, optmtyp), len) = MessageType.decode(Tag.MessageTypeTag, packet)
    lazy val msg = packet.decode
    for (mtyp <- optmtyp; handler <- jump_table(mtyp.id)) handler(msg)
  }
}

object SessionProtocol {
  case class Send(data: ByteString)
  case object Close
}

class SocketActor(cti_server: InetSocketAddress, listener: ActorRef) extends Actor {
  import akka.io.Tcp._
  import context.system

  val packetizer = new Packetizer(listener ! _)

  IO(Tcp) ! Connect(cti_server)

  def receive = {
    case CommandFailed(_: Connect) =>
      println("connect to cti server failed.")
      context stop self

    case c @ Connected(remote, local) =>
      val connection = sender()
      connection ! Register(self)
      context become {
        case Received(data) => packetizer receive data
        case data: ByteString => connection ! Write(data.withlength)
        case SessionProtocol.Send(data) => connection ! Write(data.withlength)
        case _: ConnectionClosed => context stop self
        case SessionProtocol.Close => connection ! Tcp.Close
      }
      listener ! c

  }

}




/**
  * Packetizing binary stream from TCP into entire CTI Server Protocol message.
  */
/*
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
*/