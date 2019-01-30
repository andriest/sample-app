package com.andrie.sample.snippet

import com.andrie.sample.dao.BeratDao
import com.andrie.sample.util.RichDate
import com.andrie.sample.util.RichDate.dateToRichDate
import net.liftweb.http.{RequestVar, S, SHtml}
import scala.xml.NodeSeq
import net.liftweb.util.Helpers._


/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */

private object maximumVar extends RequestVar("")
private object minimumVar extends RequestVar("")
private object dateVar extends RequestVar("")


object Interface {
    /**
      * Bulatkan nilai rata-rata menjadi 2 digit dibelakang koma
      * @param num - nilai rata-rata
      * @return
      */
    private def average(num:Double):Double = {
        BigDecimal(num).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    }

    /**
      * Digunakan untuk menampilkan daftar pada table berat
      * @return
      */
    def getList:NodeSeq = {
        val result = BeratDao.fetch(limit = 100)
        val length = result.length.toDouble

        <table>
            <thead>
                <tr>
                    <th>Tanggal</th>
                    <th>Max</th>
                    <th>Min</th>
                    <th>Perbedaan</th>
                </tr>
            </thead>
            <tbody>
                {
                    result.map { data =>
                        val max = data.maximum
                        val min = data.minimum
                        val gap = max - min
                        val date = new java.util.Date(data.ts.get.getTime)

                        <tr class="content" data-id={data.id.toString}>
                            <td>{date.toStdFormat}</td>
                            <td>{max}</td>
                            <td>{min}</td>
                            <td>{gap}</td>
                        </tr>:NodeSeq
                    }.reduceLeftOption(_ ++ _).getOrElse(NodeSeq.Empty)
                }
                <tr>
                    <td><strong>Rata-rata</strong></td>
                    <td><strong>{average(Math.round(result.map(_.maximum).sum) / length)}</strong></td>
                    <td><strong>{average(Math.round(result.map(_.minimum).sum) / length)}</strong></td>
                    <td><strong>{average(Math.round(result.map(x => x.maximum - x.minimum).sum) / length)}</strong></td>
                </tr>
            </tbody>
        </table>
    }

    /**
      * Digunakan untuk menambah / mengubah data berat
      * @param in - NodeSeq element
      * @return
      */
    def submitData(in:NodeSeq):NodeSeq = {
        val id = S.param("id").openOr("0").toLong
        val isEdited = id != 0
        val getData = BeratDao.getById(id)

        if (isEdited) {
            getData.foreach { data =>
                val date = new java.util.Date(data.ts.get.getTime)
                dateVar.set(date.toStdFormat)
                maximumVar.set(data.maximum.toString)
                minimumVar.set(data.minimum.toString)
            }
        }

        def submitInternal():Unit = {
            val max = maximumVar.is.trim.toInt
            val min = minimumVar.is.trim.toInt

            val dateLong = RichDate.standardDateFormat.parse(dateVar.is.trim).getTime

            if (isEdited) {
                getData.foreach { data =>
                    val updated = data.copy(maximum = max, minimum = min, ts = Some(new java.sql.Timestamp(dateLong)))
                    BeratDao.update(data.id, updated)
                }
            } else {
                BeratDao.addBerat(dateLong, max, min)
            }

            S.redirectTo(if (isEdited) "/view/" + getData.map(_.id).getOrElse(0).toString else "/", () => S.notice("Success"))
        }

        SHtml.ajaxForm(
            bind("in", in,
                "date" -> SHtml.text(dateVar, dateVar(_), "class" -> "form-control", "placeholder" -> "Tanggal (yyyy-MM-dd)"),
                "max" -> SHtml.text(maximumVar, maximumVar(_), "class" -> "form-control", "placeholder" -> "Berat Max"),
                "min" -> SHtml.text(minimumVar, minimumVar(_), "class" -> "form-control", "placeholder" -> "Berat Min"),
                "submit" -> {
                    SHtml.hidden(submitInternal) ++
                        SHtml.submit(if (isEdited) "Update" else "Add", () => Unit, "class" -> "submit-button")
                }
            )
        )
    }

    /**
      * Digunakan untuk menampilkan data berdasarkan id-nya
      * @return
      */
    def getInfo:NodeSeq = {
        val id = S.param("id").openOr("0").toLong
        BeratDao.getById(id).map { data =>
            val max = data.maximum
            val min = data.minimum
            val gap = max - min
            val date = new java.util.Date(data.ts.get.getTime)

            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Tanggal</th>
                            <th>{date.toStdFormat}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Max</td>
                            <td>{max}</td>
                        </tr>
                        <tr>
                            <td>Min</td>
                            <td>{min}</td>
                        </tr>
                        <tr>
                            <td>Perbedaan</td>
                            <td>{gap}</td>
                        </tr>
                    </tbody>
                </table>
                <p class="edit-button"><a href={"/view/" + data.id.toString + "/edit"}>Edit</a></p>
            </div>:NodeSeq
        }.getOrElse {
            <div>No Data</div>:NodeSeq
        }
    }
}
