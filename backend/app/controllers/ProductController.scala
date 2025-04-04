package controllers

import play.api.libs.json._
import play.api.mvc._
import services._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject()(
	                                 val controllerComponents: ControllerComponents,
	                                 productService: IProductService
                                 )(implicit ec: ExecutionContext)
	extends BaseController {
	
	def health(): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] =>
			Future.successful(Ok("All is working well"))
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
		implicit request: Request[AnyContent] =>
			// TODO: add  model validation   and implementation
			Future.successful(NotImplemented)
	}
	
	def update(id: Long): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] =>
			// TODO: add  model validation   and implementation
			Future.successful(NotImplemented)
	}
	
	def delete(id: Long): Action[AnyContent] = Action.async {
		implicit request: Request[AnyContent] =>
			productService.deleteProduct(id).map {
				case true => Ok(Json.obj("message" -> s"product with id $id has be deleted"))
				case false => NotFound(Json.obj("message" -> s"product with id $id not found  "))
			}
	}
}
