package ctidriver

import akka.stream._
import akka.stream.stage._
import akka.util.ByteString

/**
  * Created by nshimaza on 2016/04/03.
  */
class CtiFramingStage extends GraphStage[FlowShape[ByteString, ByteString]] {

  val in = Inlet[ByteString]("CtiFramingStage.in")
  val out = Outlet[ByteString]("CtiFramingStage.out")

  override val shape = FlowShape.of(in, out)

  override def createLogic(attr: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {

      var packetizer = Packetizer()

      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          packetizer = packetizer(grab(in))
          if (packetizer.isOutOfSync) {
            failStage(new Exception("CTI massage frame out of sync"))
          } else if (packetizer.packets.isEmpty) {
            pull(in)
          } else {
            emitMultiple(out, packetizer.packets)
          }
        }
      })

      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          pull(in)
        }
      })
    }
}
