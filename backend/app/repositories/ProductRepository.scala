package repositories

import models.Product

import scala.concurrent.Future

trait ProductRepository {
	def list(): Future[List[Product]]
	
	def getOne(id: Long): Future[Option[Product]]
	
	def create(product: Product): Future[Product]
	
	def update(id: Long, product: Product): Future[Option[Product]]
	
	def delete(id: Long): Future[Boolean]
}