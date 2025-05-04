//https://www.playframework.com/documentation/3.0.x/ScalaTestingWithScalaTest#Unit-Testing-Controllers
// this tests are majorly testing happy paths
// TODO: add test to failed cases ie notfound, badrequests etc 

package scala.controllers


import controllers.ProductController
import models.{CreateProductDto, Product, ProductDetail}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.IProductService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ProductControllerSpec extends PlaySpec with MockitoSugar {
	
	val mockProductService = mock[IProductService]
	
	val controller = new ProductController(Helpers.stubControllerComponents(), mockProductService)
	
	val product1 = Product(
		1L, "Product 1", "laptops", "111", BigDecimal(12.13),
		Option(List(ProductDetail("Cpu", "16 core")))
	)
	
	val product2 = Product(
		2L, "Product 1", "laptops", "222", BigDecimal(12.14),
		Option(List(ProductDetail("Cpu", "16 core")))
	)
	
	val newProduct = CreateProductDto(
		"Product 1", "laptops", "222", BigDecimal(12.14),
		Option(List(ProductDetail("Cpu", "16 core")))
	)
	
	val createdProduct = Product(
		id = 3L, "Product 1", "laptops", "222", BigDecimal(12.14),
		Option(List(ProductDetail("Cpu", "16 core")))
	)
	
	val updatedProduct = product1.copy(name = "Updated Product 1")
	
	"Product controller" should {
		
		"return health status" in {
			val request = FakeRequest(Helpers.GET, "/health")
			val result = controller.health()(request)
			status(result) mustBe Status.OK
		}
		
		"list all products" in {
			when(mockProductService.listProducts()) thenReturn Future.successful(List(product1, product2))
			
			val request = FakeRequest(Helpers.GET, "/products")
			val result = controller.list()(request)
			
			status(result) mustBe Status.OK
			contentAsJson(result).as[Seq[Product]] must contain theSameElementsAs List(product1, product2)
		}
		
		"get one single product by id" in {
			when(mockProductService.getProduct(1L)).thenReturn(Future.successful(Some(product1)))
			
			val requestFound = FakeRequest(Helpers.GET, "/products/1")
			val resultFound = controller.get(1L)(requestFound)
			
			status(resultFound) mustBe Status.OK
			contentAsJson(resultFound).as[Product] mustBe product1
		}
		
		"create a new product" in {
			when(mockProductService.createProduct(any())).thenReturn(Future.successful(createdProduct))
			
			val request = FakeRequest(Helpers.POST, "/products").withJsonBody(Json.toJson(newProduct))
			val result = controller.create()(request)
			
			status(result) mustBe Status.CREATED
			contentAsJson(result).as[Product] mustBe createdProduct
		}
		
		"update an existing product" in {
			when(mockProductService.updateProduct(any(), any())).thenReturn(Future.successful(Some(updatedProduct)))
			
			val request = FakeRequest(Helpers.PUT, "/products/1").withJsonBody(Json.toJson(updatedProduct))
			val result = controller.update(1L)(request)
			
			status(result) mustBe Status.OK
			contentAsJson(result).as[Product] mustBe updatedProduct
		}
		
		"delete an existing product" in {
			when(mockProductService.deleteProduct(1L)).thenReturn(Future.successful(true))
			
			val request = FakeRequest(Helpers.DELETE, "/products/1")
			val result = controller.delete(1L)(request)
			
			status(result) mustBe Status.OK
		}
	}
}