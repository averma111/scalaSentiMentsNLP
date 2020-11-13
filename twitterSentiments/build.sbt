name := "twitterSentiments"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.3.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"
libraryDependencies += "org.apache.bahir" %% "spark-streaming-twitter" % "2.3.0"
//libraryDependencies += "edu.stanford.nlp" %% "stanford-corenlp" % "3.6.0"
//libraryDependencies += "edu.stanford.nlp" %% "stanford-corenlp" % "3.6.0" classifier "models"

libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2"

libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2" classifier "models"

// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.14.0"

// https://mvnrepository.com/artifact/com.cybozu.labs/langdetect
libraryDependencies += "com.cybozu.labs" % "langdetect" % "1.1-20120112"


// https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch-spark-20
libraryDependencies += "org.elasticsearch" %% "elasticsearch-spark-20" % "7.10.0"

resolvers += "Akka Repository" at "https://repo.akka.io/releases/"