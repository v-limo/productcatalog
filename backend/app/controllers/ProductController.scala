package controllers

import models._
import play.api.libs.json._
import play.api.mvc._
import services._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents, productService: IProductService
                                 )(implicit ec: ExecutionContext)
	extends BaseController {
	
	def health() = Action {
		implicit _ =>
			Ok(Json.obj(
				"message" -> "All is working well",
				"available endpoints" -> List(" GET      /", " GEt      /products", " POST     /products/:id", " PUT      /products/:id", " DELETe   /products/:id")))
	}
	
	def list() = Action.async {
		implicit _ =>
			productService.listProducts().map(products => Ok(Json.toJson(products)))
	}
	
	def get(id: Long) = Action.async {
		implicit _ =>
			productService.getProduct(id).map {
				case Some(product) => Ok(Json.toJson(product))
				case None => NotFound(Json.obj("message" -> s"Product with the id ${id} not found"))
			}
	}
	
	def create() = Action.async {
		implicit request => {
			
			val jsonOption = request.body.asJson
			
			jsonOption match {
				case Some(json) =>
					val validResults = json.validate[CreateProductDto]
					validResults match {
						case JsSuccess(value, _) =>
							val createdProduct = productService.createProduct(value)
							createdProduct.map(p => Created(Json.toJson(p)))
						case JsError(errors) => Future.successful(BadRequest(Json.toJson(JsError.toJson(errors))))
					}
				case None => Future.successful(BadRequest(Json.obj("message" -> "Expecting a json")))
			}
		}
	}
	
	def update(id: Long) = Action.async {
		implicit request => {
			val jsonOption = request.body.asJson
			
			jsonOption match {
				case Some(json) =>
					val validationResult = json.validate[Product]
					validationResult match {
						case JsSuccess(value, _) =>
							if (value.id != id)
								Future.successful(BadRequest(
									Json.obj("message" -> "id in the body should match that in the request path")))
							else {
								val future = productService.updateProduct(id, value)
								future.map {
									case Some(updatedProduct) => Ok(Json.toJson(updatedProduct))
									case None => NotFound(Json.obj("message" -> s"Product with the id $id not found "))
								}
							}
						case JsError(errors) => Future.successful(BadRequest(Json.toJson(JsError.toJson(errors))))
					}
				case None => Future.successful(BadRequest(Json.obj("message" -> "Expecting json")))
			}
		}
	}
	
	def delete(id: Long) = Action.async {
		implicit _ =>
			productService.deleteProduct(id).map {
				case true => Ok(Json.obj("message" -> s"product with id $id has be deleted"))
				case false => NotFound(Json.obj("message" -> s"product with id $id not found  "))
			}
	}
}	
