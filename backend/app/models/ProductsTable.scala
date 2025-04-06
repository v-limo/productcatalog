package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Json
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import javax.inject.Inject

trait ProductsTable extends HasDatabaseConfigProvider[JdbcProfile] {
	val products: TableQuery[Products] = TableQuery[Products]
	
	import profile.api._
	
	implicit val productDetailListColumnType = {
		MappedColumnType.base[List[ProductDetail], String](
			list => Json.stringify(Json.toJson(list)),
			jsonString => Json.parse(jsonString).as[List[ProductDetail]])
	}
	@Inject() protected val dbConfigProvider: DatabaseConfigProvider
	
	class Products(tag: Tag) extends Table[Product](tag, _tableName = "product") {
		
		override def * : ProvenShape[Product] = (id, name, category, code, price, details) <> ((Product.apply _).tupled, Product.unapply)
		
		def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
		
		private def name: Rep[String] = column[String]("name")
		
		private def category: Rep[String] = column[String]("category")
		
		private def code: Rep[String] = column[String]("code")
		
		private def price: Rep[BigDecimal] = column[BigDecimal]("price")
		
		private def details: Rep[Option[List[ProductDetail]]] = column[Option[List[ProductDetail]]]("details")
	}
}