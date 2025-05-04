package repositories

import models._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

trait IProductRepository {
	def list(): Future[List[Product]]

	def getOne(id: Long): Future[Option[Product]]

	def create(product: CreateProductDto): Future[Product]

	def update(id: Long, product: Product): Future[Option[Product]]

	def delete(id: Long): Future[Boolean]
}

@Singleton
class ProductRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
	extends HasDatabaseConfigProvider[JdbcProfile]
		with IProductRepository with ProductsTable {

	import profile.api._

	def list() = db.run(products.result.map(_.toList))

	def getOne(id: Long) = db.run(products.filter(_.id === id).result.headOption)

	def delete(id: Long) = db.run(products.filter(_.id === id).delete.map(_ > 0))

	def create(product: CreateProductDto) = {
		val productToInsert: Product = Product(
			id = Long.MaxValue, // will be replace by the db so it is okay for now,
			name = product.name,
			category = product.category,
			code = product.code,
			price = product.price,
			details = product.details,
		)
		val eventualLong = db.run((products returning products.map(_.id)) += productToInsert)
		val productFuture = eventualLong.map(newId => productToInsert.copy(id = newId))
		productFuture
	}

	def update(id: Long, product: Product) = {
		val productToUpdate = product.copy(id = id)
		db.run(products.filter(_.id === id).update(productToUpdate)).flatMap(rowsUpdated => {
			if (rowsUpdated > 0) getOne(id)
			else Future.successful(None)
		})
	}
}