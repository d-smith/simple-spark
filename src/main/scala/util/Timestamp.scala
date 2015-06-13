//Adapted from TypeSafe training sample
package util

import java.util.Date
import java.text.SimpleDateFormat

object Timestamp {
  val fmt = new SimpleDateFormat ("yyyy.MM.dd-kk.mm.ss");

  def now(): String =
    fmt.format(new Date())
}