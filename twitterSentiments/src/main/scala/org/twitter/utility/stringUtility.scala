package org.twitter.utility

object stringUtility {

  def onlyWords(str :String) :String ={

    str.split(" ").filter(_.matches("^[a-zA-Z0-9 ]+$")).fold("")((a,b) => a + " " + b).trim
  }
}