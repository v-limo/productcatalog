package modules

import com.google.inject.AbstractModule
import repositories._
import services._

class AppModule extends AbstractModule {
	override def configure(): Unit = {
		bind(classOf[IProductRepository])
			.to(classOf[ProductRepository])
		
		bind(classOf[IProductService])
			.to(classOf[ProductService])
	}
}