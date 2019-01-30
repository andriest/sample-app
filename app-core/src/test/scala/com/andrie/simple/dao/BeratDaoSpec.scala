package com.andrie.simple.dao

import java.util.Date
import com.andrie.sample.dao.BeratDao
import com.andrie.sample.model.Tables


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
class BeratDaoSpec extends DaoSpec {
    sequential

    "Berat Dao" should {
        "Add data" in {
            val data = BeratDao.addBerat(new Date().getTime, 50, 45)

            data must anInstanceOf[Tables.Berat]
            data.id must not be null
            data.maximum mustEqual 50
        }

        "get a some data" in {
            val data = BeratDao.addBerat(new Date().getTime, 67, 56)

            data must anInstanceOf[Tables.Berat]
            BeratDao.getById(data.id) must_== Some(data)
        }

        "update data must return updated record" in {
            val data = BeratDao.addBerat(new Date().getTime, 66, 55)

            val change = data.copy(maximum = 60, minimum = 40)
            val updated = BeratDao.update(data.id, change)

            updated.maximum mustEqual 60
            updated.minimum mustEqual 40
        }

        "get list data" in {
            cleanUpDb()

            val now = new Date().getTime
            BeratDao.addBerat(now, 60, 55)
            BeratDao.addBerat(now, 67, 50)
            BeratDao.addBerat(now, 59, 49)

            BeratDao.fetch().length must_== 3
        }
    }
}
