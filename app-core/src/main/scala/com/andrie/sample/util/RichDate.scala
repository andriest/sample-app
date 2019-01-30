package com.andrie.sample.util

import java.text.SimpleDateFormat
import java.util.Date


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
object RichDate {
    /**
      * Standard date format without time: yyyy-MM-dd
      */
    val standardDateFormat = new SimpleDateFormat("yyyy-MM-dd")


    class WrapRichDate(d:Date) {
        def toStdFormat:String = standardDateFormat.format(d)
    }


    implicit def dateToRichDate(d:Date):WrapRichDate = new WrapRichDate(d)
}

