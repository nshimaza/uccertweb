//package ctidriver
//
//import akka.util.ByteString
//
///**
// * Enumeration with factory func which returns Value in wrapping Option.
// */
//class ShortEnum extends Enumeration {
//  def decode(tag: Tag, buf: ByteString): (Tag, Option[Value]) = {
//    try {
//      (tag, Some(super.apply(buf.toShort.toInt & 0xffff)))
//    } catch {
//      case e: NoSuchElementException => (tag, None)
//    }
//  }
//
//  def decodeWithLen(tag: Tag, buf: ByteString) = (decode(tag, buf), 2)
//}
