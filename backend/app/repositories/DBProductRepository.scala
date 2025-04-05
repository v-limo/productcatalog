package repositories

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class DBProductRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
	extends HasDatabaseConfigProvider[JdbcProfile]
		with ProductRepository {
	
	
	override def list(): Future[List[models.Product]] = ???
	
	override def getOne(id: Long): Future[Option[models.Product]] = ???
	
	override def create(product: models.Product): Future[models.Product] = ???
	
	override def update(id: Long, product: models.Product): Future[Option[models.Product]] = ???
	
	override def delete(id: Long): Future[Boolean] = ???
}	