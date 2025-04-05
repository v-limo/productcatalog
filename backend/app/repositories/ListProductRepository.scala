package repositories

import models.Product

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ListProductRepository @Inject()()(implicit ec: ExecutionContext)
	extends ProductRepository {
	
	private var products: List[Product] = List(
		Product(1, "Laptop", "Electronics", "LAP123", 1200.00, List()),
		Product(2, "Smartphone", "Electronics", "SMT456", 800.00, List()),
		Product(3, "Tablet", "Electronics", "TAB789", 500.00, List(models.ProductDetail("ScreenSize", "10 inch")))
	)
	
	override def list(): Future[List[Product]] = Future.successful(products)
	
	override def getOne(id: Long): Future[Option[Product]] = Future.successful(products.find(_.id == id))
	
	override def create(product: Product): Future[Product] = Future.successful {
		val newProduct = product.copy(id = products.size + 1)
		products = products :+ newProduct
		newProduct
	}
	
	override def update(id: Long, product: Product): Future[Option[Product]] = Future.successful {
		products.find(_.id == id).map(_ => {
			products = products.map(p => if (p.id == id) product.copy(id = id) else p)
			product.copy(id = id)
		})
	}
	
	override def delete(id: Long): Future[Boolean] = {
		val initialSize: Int = products.size
		products = products.filterNot(_.id == id)
		val isDeleted = products.size < initialSize
		Future.successful(isDeleted)
	}
}