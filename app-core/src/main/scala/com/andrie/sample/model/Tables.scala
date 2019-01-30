package com.andrie.sample.model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}
  // base entity class
  trait Entity

  abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
      val id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  }
  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Berats.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Berat
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param maximum Database column maximum SqlType(int4), Default(0)
   *  @param minimum Database column minimum SqlType(int4), Default(0)
   *  @param modified Database column modified SqlType(timestamp)
   *  @param ts Database column ts SqlType(timestamp) */
  case class Berat(id: Long, maximum: Int = 0, minimum: Int = 0, modified: Option[java.sql.Timestamp], ts: Option[java.sql.Timestamp]) extends BeratExt with Entity
  /** GetResult implicit for fetching Berat objects using plain SQL queries */
  implicit def GetResultBerat(implicit e0: GR[Long], e1: GR[Int], e2: GR[Option[java.sql.Timestamp]]): GR[Berat] = GR{
    prs => import prs._
    Berat.tupled((<<[Long], <<[Int], <<[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table berat. Objects of this class serve as prototypes for rows in queries. */
  class BeratRow(_tableTag: Tag) extends BaseTable[Berat](_tableTag, "berat") {
    def * = (id, maximum, minimum, modified, ts) <> (Berat.tupled, Berat.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(maximum), Rep.Some(minimum), modified, ts).shaped.<>({r=>import r._; _1.map(_=> Berat.tupled((_1.get, _2.get, _3.get, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    override val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column maximum SqlType(int4), Default(0) */
    val maximum: Rep[Int] = column[Int]("maximum", O.Default(0))
    /** Database column minimum SqlType(int4), Default(0) */
    val minimum: Rep[Int] = column[Int]("minimum", O.Default(0))
    /** Database column modified SqlType(timestamp) */
    val modified: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("modified")
    /** Database column ts SqlType(timestamp) */
    val ts: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("ts")
  }
  /** Collection-like TableQuery object for table Berat */
  lazy val Berats = new TableQuery(tag => new BeratRow(tag))
}
