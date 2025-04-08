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
	
	def health(): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] =>
			Future.successful(Ok(Json.obj(
				"message" -> "All is working well",
				"available endpoints" -> List(" GET      /", " GEt      /products", " POST     /products/:id", " PUT      /products/:id", " DELETe   /products/:id")))
			)
	}
	
	def list(): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] =>
			productService.listProducts().map(products => Ok(Json.toJson(products)))
	}
	
	def get(id: Long): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] =>
			productService.getProduct(id).map {
				case Some(product) => Ok(Json.toJson(product))
				case None =>
					NotFound(
						Json.obj("message" -> s"Product with the id ${id} not found")
					)
			}
	}
	
	def create(): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] => {
			
			val jsonOption: Option[JsValue] = request.body.asJson
			
			jsonOption match {
				case Some(json) =>
					val validResults = json.validate[CreateProductDto]
					validResults match {
						case JsSuccess(value, path) =>
							val createdProduct = productService.createProduct(value)
							createdProduct.map(p => Created(Json.toJson(p)))
						case JsError(errors) => Future.successful(BadRequest(Json.toJson(JsError.toJson(errors))))
					}
				case None => Future.successful(BadRequest(Json.obj("message" -> "Expecting a json")))
			}
		}
	}
	
	def update(id: Long): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] => {
			val jsonOption: Option[JsValue] = request.body.asJson
			
			jsonOption match {
				case Some(json) =>
					val validationResult: JsResult[Product] = json.validate[Product]
					validationResult match {
						case JsSuccess(value, path) =>
							if (value.id != id)
								Future.successful(BadRequest(
									Json.obj("message" -> "id in the body should match that in the request path")))
							else {
								val future: Future[Option[Product]] = productService.updateProduct(id, value)
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
	
	def delete(id: Long): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] =>
			productService.deleteProduct(id).map {
				case true => Ok(Json.obj("message" -> s"product with id $id has be deleted"))
				case false => NotFound(Json.obj("message" -> s"product with id $id not found  "))
			}
	}
}	
