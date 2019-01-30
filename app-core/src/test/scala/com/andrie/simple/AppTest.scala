package com.andrie.simple

import com.andrie.sample.App
import com.andrie.sample.model.Tables
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import org.streum.configrity.Configuration
import slick.dbio.DBIO
import slick.driver.PostgresDriver.api._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
abstract class AppTest extends Specification {

    App.setupBasicEngine(Configuration.load("test.conf"))

}


trait UsingDb extends BeforeAfterAll {
    def cleanUpDb():Unit = {
        println("before: truncate tables...")

        val seq1 = DBIO.seq {
            Tables.Berats.delete
        }

        App.engine.dbExecute(seq1)
    }

    override def beforeAll():Unit = {
        cleanUpDb()
    }

    override def afterAll():Unit = {}
}

