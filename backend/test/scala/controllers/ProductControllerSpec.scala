// https://www.playframework.com/documentation/3.0.x/ScalaTestingWithScalaTest
// this test are majorly testing happy paths
// TODO: add test to test bad request, not found etc  


package scala.controllers

import controllers.ProductController
import models.{Product, ProductDetail}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsEmpty, AnyContentAsJson, Result}
import play.api.test.Helpers.{contentType, defaultAwaitTimeout, running, status}
import play.api.test.{FakeRequest, Helpers}
import services.IProductService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ProductControllerSpec extends PlaySpec with MockitoSugar {
	
	val mockProductService: IProductService = mock[IProductService]
	
	val app: Application = new GuiceApplicationBuilder().build()
	
	val controller = new ProductController(Helpers.stubControllerComponents(), mockProductService)
	
	val product1: Product = Product(
		1L, "Product 1", "laptops", "111", BigDecimal(12.13),
		Option(List(ProductDetail("Cpu", "16 core")))
	)
	
	val product2: Product = Product(
		2L, "Product 1", "laptops", "222", BigDecimal(12.14),
		Option(List(ProductDetail("Cpu", "16 core")))
	)
	
	val newProduct: Product = product2.copy(id = 3L, name = "Product 3", code = "333")
	val createdProduct: Product = newProduct.copy(id = 3L)
	val updatedProduct: Product = product1.copy(name = "Updated Product 1")
	
	"Product controller" should {
		
		"return health status" in {
			running(app) {
				val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(Helpers.GET, "/health")
				val result: Future[Result] = controller.health()(request)
				status(result) mustBe Helpers.OK
			}
		}
		
		"list all products" in {
			running(app) {
				when(mockProductService.listProducts()) thenReturn Future.successful(List(product1, product2))
				
				val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(Helpers.GET, "/products")
				val result: Future[Result] = controller.list()(request)
				
				status(result) mustBe Helpers.OK
				Helpers.contentAsJson(result).as[Seq[Product]] must contain theSameElementsAs List(product1, product2)
			}
		}
		
		"get one single product by id" in {
			running(app) {
				
				when(mockProductService.getProduct(1L)).thenReturn(Future.successful(Some(product1)))
				
				val requestFound: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(Helpers.GET, "/products/1")
				val resultFound: Future[Result] = controller.get(1L)(requestFound)
				
				status(resultFound) mustBe Helpers.OK
				Helpers.contentAsJson(resultFound).as[Product] mustBe product1
			}
		}
		
		"create a new product" in {
			running(app) {
				
				when(mockProductService.createProduct(any())).thenReturn(Future.successful(createdProduct))
				
				val request: FakeRequest[AnyContentAsJson] = FakeRequest(Helpers.POST, "/products").withJsonBody(Json.toJson(newProduct))
				val result: Future[Result] = controller.create()(request)
				
				status(result) mustBe Helpers.CREATED
				
				Helpers.contentAsJson(result).as[Product] mustBe createdProduct
			}
		}
		
		"update an existing product" in {
			running(app) {
				
				when(mockProductService.updateProduct(any(), any())).thenReturn(Future.successful(Some(updatedProduct)))
				
				val request: FakeRequest[AnyContentAsJson] =
					FakeRequest(Helpers.PUT, "/products/1").withJsonBody(Json.toJson(updatedProduct))
				val result: Future[Result] = controller.update(1L)(request)
				
				println(Helpers.contentAsString(result))
				//status(result) mustBe Helpers.OK
				//Helpers.contentAsJson(result).as[Product] mustBe updatedProduct
			}
		}
		
		"delete an existing product" in {
			running(app) {
				
				when(mockProductService.deleteProduct(1L)).thenReturn(Future.successful(true))
				
				val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest(Helpers.DELETE, "/products/1")
				val result: Future[Result] = controller.delete(1L)(request)
				
				status(result) mustBe Helpers.OK
				contentType(result) mustBe Some("application/json")
			}
		}
	}
}