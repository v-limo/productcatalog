package repositories

import models._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DBProductRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
	extends HasDatabaseConfigProvider[JdbcProfile]
		with ProductRepository
		with ProductsTable {
	
	import profile.api._
	
	def list(): Future[List[Product]] = db.run(products.result.map(_.toList))
	
	def getOne(id: Long): Future[Option[Product]] = db.run {
		products.filter(_.id === id).result.headOption
	}
	
	def create(product: Product): Future[Product] = {
		db.run((products returning products.map(_.id)) += product)
			.map(newId => product.copy(id = newId))
	}
	
	def update(id: Long, product: Product): Future[Option[Product]] = {
		val productToUpdate = product.copy(id = id)
		db.run(products.filter(_.id === id).update(productToUpdate)).flatMap(rowsUpdated => {
			if (rowsUpdated > 0) getOne(id)
			else Future.successful(None)
		})
	}
	
	def delete(id: Long): Future[Boolean] = db.run {
		products.filter(_.id === id).delete.map(_ > 0)
	}
}	