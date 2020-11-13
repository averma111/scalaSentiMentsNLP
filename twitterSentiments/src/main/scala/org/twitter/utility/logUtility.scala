package org.twitter.utility

import org.apache.log4j.{Level, Logger}
import org.apache.spark.internal.Logging

object logUtility extends Logging {

  def setRealtimeLogLevel(): Unit = {

    val log4j = Logger.getRootLogger.getAllAppenders.hasMoreElements

    if (!log4j) {

      logInfo("Setting log level to [WARN] for realtime example.")

      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }
}