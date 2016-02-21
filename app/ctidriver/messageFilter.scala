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

import akka.util.ByteString
import ctidriver.MessageType._

/**
  * Created by nshimaza on 2016/02/21.
  */
case class MessageFilterEntry(set: Set[MessageType], handler: (Message) => Unit)

case class MessageFilter(filterConf: Traversable[MessageFilterEntry]) {
  val handlerTable: Array[Traversable[Message => Unit]] = {
    val baseList = MessageType.values.toList map (_.id) map ((_, Traversable[Message => Unit]()))
    val flatList = for {
      entry <- filterConf.toList
      messageType <- entry.set
    } yield (messageType.id, List(entry.handler))
    val optimizedMap = (baseList ++ flatList) groupBy (_._1) map (t => (t._1, t._2 flatMap (_._2)))
    (optimizedMap.toSeq sortWith (_._1 < _._1) map (_._2)).toArray
  }

  def apply(packet: ByteString): Traversable[Message => Unit] = {
    val ((tag, haveMessageType), len) = MessageType.decode(Tag.MessageTypeTag, packet)
    (for (messageType <- haveMessageType) yield handlerTable(messageType.id)
      ).getOrElse(Traversable[Message => Unit]())
  }
}
