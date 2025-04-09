//
//// https://www.playframework.com/documentation/3.0.x/ScalaTestingWithDatabases
//// this test are majorly testing happy paths
//// TODO: add test to test edge cases etc 
//
//package scala.repositories
//
//import models.{CreateProductDto, Product}
//import org.scalatest.BeforeAndAfterEach
//import org.scalatestplus.play.PlaySpec
//import play.api.Application
//import play.api.inject.guice.GuiceApplicationBuilder
//import play.api.test.Helpers.running
//import play.api.test.{HasApp, Injecting}
//import repositories.IProductRepository
//
//import scala.concurrent.Await
//import scala.concurrent.duration._
//import scala.language.postfixOps
//
//// TODO: Fix the issue with 'class required but found T' ??
//// Bug: needs to be fixed!!!  
//class ProductRepositorySpec extends PlaySpec with Injecting with HasApp with BeforeAndAfterEach {
//	
//	def application: Application = new GuiceApplicationBuilder().build()
//	
//	implicit val timeout: Duration = 5 seconds
//	
//	override def afterEach(): Unit = {
//		cleanupDatabase()
//	}
//	
//	def cleanupDatabase(): Unit = {
//		val repository = inject[IProductRepository]
//		Await.result(repository.delete(1), timeout) ???
//	}
//	
//	
//	"product repository  " should {
//		
//		"create a product" in running(application) {
//			val repository = inject[IProductRepository]
//			
//			val newProduct = CreateProductDto("Product 1", "Cat 1", "P1", BigDecimal(10), None)
//			
//			val created = Await.result(repository.create(newProduct), timeout)
//			
//			created.name mustBe "Product 1"
//		}
//		
//		"get all products" in running(application) {
//			val repository: IProductRepository = inject[IProductRepository]
//			
//			val product = CreateProductDto("Product 1", "Cat 1", "P1", BigDecimal(10), None)
//			
//			Await.result(repository.create(product), timeout)
//			
//			val allProducts: List[Product] = Await.result(repository.list(), timeout)
//			allProducts.size mustBe 1
//		}
//		
//		"get one product by id" in running(application) {
//			val repository = inject[IProductRepository]
//			
//			val product = CreateProductDto("Product 1", "Cat 1", "P1", BigDecimal(10), None)
//			
//			val created: Product = Await.result(repository.create(product), timeout)
//			
//			val found: Option[Product] = Await.result(repository.getOne(created.id), timeout)
//			
//			found mustBe Some(created)
//		}
//		
//		"update a product" in running(application) {
//			val repository = inject[IProductRepository]
//			
//			val product = CreateProductDto("Product 1", "Cat 1", "P1", BigDecimal(10), None)
//			
//			val created = Await.result(repository.create(product), timeout)
//			
//			val updatedProduct: Product = created.copy(name = "Updated Product 1", price = BigDecimal(25))
//			
//			val updated: Option[Product] = Await.result(repository.update(created.id, updatedProduct), timeout)
//			
//			updated.isDefined mustBe true
//			updated.get.name mustBe "Updated Product 1"
//			updated.get.price mustBe BigDecimal(25)
//		}
//		
//		"delete a product" in running(application) {
//			val repository = inject[IProductRepository]
//			
//			val product = CreateProductDto("Product 1", "Cat 1", "P1", BigDecimal(10), None)
//			
//			Await.result(repository.create(product), timeout)
//			
//			val deletionResult: Boolean = Await.result(repository.delete(product.id), timeout)
//			
//			deletionResult mustBe true
//		}
//	}
//}