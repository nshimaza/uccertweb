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
import javax.inject.Inject

import akka.actor.{Terminated, Actor, Props}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


class HAActorImpl (sessionActorPropsFactory: SessionActorPropsFactory,
                   serverA: InetSocketAddress,
                   serverB: InetSocketAddress,
                   filter: MessageFilter)
                  (implicit executor: ExecutionContext)
  extends Actor {

  var currentServer = List(serverA, serverB)

  def receive = opening

  var sessionActor = context.actorOf(sessionActorPropsFactory(currentServer.head, filter))
  context.watch(sessionActor)


  def opening: Receive = {
    case SessionActorProtocol.SessionEstablished =>
      context become established

    case s: SessionActorProtocol.SessionAborted =>

    case Terminated(childActor) =>
      context.system.scheduler.scheduleOnce(5.seconds, self, HAActorImpl.BackoffTimeout)
      context become backoff
  }

  def established: Receive = {
    case Terminated(childActor) =>
      currentServer = currentServer.reverse
      context.unwatch(sessionActor)
      sessionActor = context.actorOf(sessionActorPropsFactory(currentServer.head, filter))
      context.watch(sessionActor)
      context become opening
  }

  def backoff: Receive = {
    case HAActorImpl.BackoffTimeout =>
      currentServer = currentServer.reverse
      context.unwatch(sessionActor)
      sessionActor = context.actorOf(sessionActorPropsFactory(currentServer.head, filter))
      context.watch(sessionActor)
      context become opening
  }
}

trait HAActorPropsFactory {
  def apply(serverA: InetSocketAddress, serverB: InetSocketAddress, filter: MessageFilter)
           (implicit executor: ExecutionContext): Props
}

class HAActorImplPropsFactory @Inject()(sessionActorPropsFactory: SessionActorPropsFactory)
  extends HAActorPropsFactory {
  def apply(serverA: InetSocketAddress, serverB: InetSocketAddress, filter: MessageFilter)
           (implicit executor: ExecutionContext) = {
    Props(classOf[HAActorImpl], sessionActorPropsFactory, serverA, serverB, filter, executor)
  }
}

object HAActorImpl {

  case object BackoffTimeout

}