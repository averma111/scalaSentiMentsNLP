package org.twitter.application

import com.cybozu.labs.langdetect.DetectorFactory
import org.twitter.utility._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.elasticsearch.spark._
import org.apache.spark.streaming.twitter.TwitterUtils
import org.twitter.utility.sentimentAnalysis.detectSentiment

import scala.util.Try

object twitterSentiments {

  def main(args: Array[String]) {

    /*if (args.length < 4) {
      System.err.println("Usage: TwitterSentimentAnalysis <consumer key> <consumer secret> " +
        "<access token> <access token secret> [<filters>]")
      System.exit(1)
    }*/

    logUtility.setRealtimeLogLevel()

    DetectorFactory.loadProfile("src/main/resources/profiles")

   // val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
    //val filters = args.takeRight(args.length - 4)

    // can use them to generate OAuth credentials
    System.setProperty("twitter4j.oauth.consumerKey", "d0bnG2XqijRoLe7m1iMM7dq5K")
    System.setProperty("twitter4j.oauth.consumerSecret", "AIPdPQikRl5iMOrayMpNiSsrdwghdBZdU5RXGRWuEZRPLPMOXs")
    System.setProperty("twitter4j.oauth.accessToken", "1325446615184797697-nw5rnV186U9wXkShZ4gWYg5nPR8VYq")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "0YObk7eOYlewJKz11LY7yAmDv5aTVMFGDMuO9XRTTam9D")

    val conf = new SparkConf().setAppName("TwitterSentimentAnalysis").setMaster("local[*]")
    conf.set("es.nodes", "localhost")

    val ssc = new StreamingContext(conf, Seconds(1))

    val tweets = TwitterUtils.createStream(ssc, None)

    tweets.print()

    tweets.foreachRDD{(rdd, time) =>
      rdd.map(t => {
        Map(
          "user"-> t.getUser.getScreenName,
          "created_at" -> t.getCreatedAt.toInstant.toString,
          "location" -> Option(t.getGeoLocation).map(geo => { s"${geo.getLatitude},${geo.getLongitude}" }),
          "text" -> t.getText,
          "hashtags" -> t.getHashtagEntities.map(_.getText),
          "retweet" -> t.getRetweetCount,
          "language" -> detectLanguage(t.getText),
          "sentiment" -> detectSentiment(t.getText).toString
        )
      }).saveToEs("twitter/tweet")
    }

    ssc.start()
    ssc.awaitTermination()

  }

  def detectLanguage(text: String) : String = {

    Try {
      val detector = DetectorFactory.create()
      detector.append(text)
      detector.detect()
    }.getOrElse("unknown")

  }

}