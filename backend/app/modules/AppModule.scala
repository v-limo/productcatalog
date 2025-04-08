package modules

import com.google.inject.AbstractModule
import repositories._
import services._

class AppModule extends AbstractModule {
	override def configure(): Unit = {
		//		 DI for database 	
		bind(classOf[IProductRepository])
			.to(classOf[ProductRepository])
		
		//		 DI for business layer 	
		bind(classOf[IProductService])
			.to(classOf[ProductService])
	}
}