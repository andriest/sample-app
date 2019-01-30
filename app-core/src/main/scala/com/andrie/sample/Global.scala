package com.andrie.sample

import org.streum.configrity.Configuration
import slick.driver.PostgresDriver
import slick.driver.PostgresDriver.api._
import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
trait Engine {
    val jdbcUrl:String

    lazy val db:PostgresDriver.backend.DatabaseDef = {
        Database.forURL(jdbcUrl, driver = "org.postgresql.Driver")
    }

    def dbExecute[R, S <: slick.dbio.NoStream, E <: slick.dbio.Effect](action:DBIOAction[R, S, E]) = {
        Await.result(db.run(action), 5.seconds)
    }
}


object App {
    var engine:Engine = _
    var conf:Configuration = _

    def setupBasicEngine(conf:Configuration):Unit = {
        this.conf = conf
        this.engine = BasicEngine(conf)
    }
}


case class BasicEngine(conf:Configuration) extends Engine {
    override val jdbcUrl:String = "jdbc:postgresql://" + conf[String]("database.host") +
        "/" + conf[String]("database.name") + "?user=" + conf[String]("database.user") +
        "&password=" + conf[String]("database.password")
}

