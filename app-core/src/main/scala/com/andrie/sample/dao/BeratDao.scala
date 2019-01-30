package com.andrie.sample.dao

import java.sql.Timestamp
import com.andrie.sample.App
import com.andrie.sample.exc.NotExistException
import com.andrie.sample.model.Tables
import slick.driver.PostgresDriver.api._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
object BeratDao extends BaseDao[Tables.BeratRow, Tables.Berat] {
    override val tableReference = Tables.Berats

    /**
      * Add data to table berat
      * @param ts - Timestamp
      * @param max - max value
      * @param min - min value
      * @return
      */
    def addBerat(ts:Long, max:Int, min:Int):Tables.Berat = {
        val sqlAdd = Tables.Berats.map(b => (b.maximum, b.minimum, b.ts))
            .returning(Tables.Berats.map(_.id)) += (max, min, Some(new Timestamp(ts)))

        val bId = App.engine.dbExecute(sqlAdd)
        this.getById(bId).getOrElse {
            throw NotExistException("no data")
        }
    }
}

