package services

import models._
import repositories._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

trait IProductService {
	def listProducts(): Future[List[Product]]
	
	def getProduct(id: Long): Future[Option[Product]]
	
	def createProduct(createDto: CreateProductDto): Future[Product]
	
	def updateProduct(id: Long, product: Product): Future[Option[Product]]
	
	def deleteProduct(id: Long): Future[Boolean]
}


@Singleton
class ProductService @Inject()(productRepository: IProductRepository)(implicit ec: ExecutionContext) extends IProductService {
	override def listProducts(): Future[List[Product]] = {
		productRepository.list()
	}
	
	override def getProduct(id: Long): Future[Option[Product]] = {
		productRepository.getOne(id)
	}
	
	override def createProduct(createDto: CreateProductDto): Future[Product] = {
		productRepository.create(createDto)
	}
	
	override def updateProduct(id: Long, product: Product): Future[Option[Product]] = {
		// TODO: update ony columns/fields with values - needs a Dto	
		productRepository.update(id, product)
	}
	
	override def deleteProduct(id: Long): Future[Boolean] = {
		productRepository.delete(id)
	}
}


	