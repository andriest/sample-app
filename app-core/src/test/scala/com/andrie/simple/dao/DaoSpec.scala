package com.andrie.simple.dao

import java.util.concurrent.atomic.AtomicLong
import com.andrie.simple.{AppTest, UsingDb}


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
abstract class DaoSpec extends AppTest with UsingDb {
    def incLong():Long = DaoSpec.incrementalNum.incrementAndGet()
}


object DaoSpec {
    protected val incrementalNum = new AtomicLong(0L)
}

