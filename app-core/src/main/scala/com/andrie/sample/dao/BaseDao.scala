package com.andrie.sample.dao

import com.andrie.sample.App
import com.andrie.sample.model.Tables
import com.andrie.sample.util.SortDirection
import com.andrie.sample.util.SortDirection.DESC
import slick.lifted.{CompiledFunction, Query, Rep, TableQuery}
import slick.driver.PostgresDriver.api._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
trait BaseDao[T <: Tables.BaseTable[A], A <: Tables.Entity] {
    type CompiledQuery = CompiledFunction[Rep[Long] => Query[T, T#TableElementType, Seq], Rep[Long], Long, Query[T, T#TableElementType, Seq], Seq[T#TableElementType]]

    val tableReference:TableQuery[T]

    lazy val queryById:CompiledQuery = tableQueryToTableQueryExtensionMethods(tableReference).findBy(t => t.id)

    /**
      * Get data by id
      * @param id - id data
      * @return
      */
    def getById(id:Long):Option[T#TableElementType] = {
        App.engine.dbExecute(queryById(id).result).headOption
    }

    /**
      * Update data by id
      * @param id - id data
      * @param o - object data
      * @return
      */
    def update(id:Long, o:T#TableElementType):T#TableElementType = {
        App.engine.dbExecute(queryById(id).update(o))
        getById(id).get
    }

    /**
      * Fetch data and ordering
      * @param offset - start offset
      * @param limit - end limit
      * @param order - sorting by ASC or DESC
      * @return
      */
    def fetch(offset:Int = 0, limit:Int = 10, order:SortDirection = SortDirection.ASC):Seq[T#TableElementType] = {
        val tbOrdered = order match {
            case DESC => tableReference.sortBy(_.id.desc.nullsDefault)
            case _ => tableReference.sortBy(_.id.asc.nullsDefault)
        }

        App.engine.dbExecute(tbOrdered.drop(offset).take(limit).result)
    }
}

