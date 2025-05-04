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
	
	override def listProducts() = productRepository.list()
	
	override def getProduct(id: Long) = productRepository.getOne(id)
	
	override def createProduct(createDto: CreateProductDto) = productRepository.create(createDto)
	
	override def updateProduct(id: Long, product: Product) = productRepository.update(id, product)
	
	override def deleteProduct(id: Long) = productRepository.delete(id)
}