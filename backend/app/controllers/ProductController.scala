package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
	
	def health(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
		Ok("All is working well")
	}
}
