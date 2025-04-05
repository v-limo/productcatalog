package repositories

import models.Product

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class DBProductRepository @Inject()()(implicit ec: ExecutionContext)
	extends ProductRepository {
	
	override def list(): Future[List[Product]] = ???
	
	override def getOne(id: Long): Future[Option[Product]] = ???
	
	override def create(product: Product): Future[Product] = ???
	
	override def update(id: Long, product: Product): Future[Option[Product]] = ???
	
	override def delete(id: Long): Future[Boolean] = ???
}