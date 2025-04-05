package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.{ProvenShape, Tag}

import javax.inject.Inject

trait ProductsTable extends HasDatabaseConfigProvider[JdbcProfile] {
	@Inject() protected def dbConfigProvider: DatabaseConfigProvider
	
	class Products(tag: Tag) extends Table[Product](tag, "product") {
		def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
		
		def name: Rep[String] = column[String]("name")
		
		def category: Rep[String] = column[String]("category")
		
		def code: Rep[String] = column[String]("code")
		
		def price: Rep[BigDecimal] = column[BigDecimal]("price")
		
		// TODO: add this colymn 
		// def details: Rep[List[ProductDetail]] = column[List[ProductDetail]] ("details")
		
		// https://shorturl.at/H7mxj
		override def * : ProvenShape[Product] = (id, name, category, code, price) <> ((Product.apply _).tupled, Product.unapply)
	}
	
	val products: TableQuery[Products] = TableQuery[Products]
}
	