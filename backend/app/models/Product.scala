package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json._
import slick.jdbc.JdbcProfile
import slick.lifted.{ProvenShape, TableQuery}

import javax.inject.Inject

case class ProductDetail(key: String, value: String)

object ProductDetail {
	implicit val format: OFormat[ProductDetail] = Json.format[ProductDetail]
}

case class Product(
	                  id: Long,
	                  name: String,
	                  category: String,
	                  code: String,
	                  price: BigDecimal,
	                  details: Option[List[ProductDetail]] = Option(List.empty)
                  )

object Product {
	implicit val format: OFormat[Product] = Json.format[Product]
}

case class CreateProductDto(
	                           name: String,
	                           category: String,
	                           code: String,
	                           price: BigDecimal,
	                           details: Option[List[ProductDetail]] = Option(List.empty)
                           )

object CreateProductDto {
	implicit val format: OFormat[CreateProductDto] = Json.format[CreateProductDto]
}

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
