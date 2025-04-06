package modules

import com.google.inject.AbstractModule
import repositories._
import services._

class AppModule extends AbstractModule {
	override def configure(): Unit = {
		// DI for in memory list 	
		//		bind(classOf[ProductRepository])
		//			.to(classOf[ListProductRepository])
		
		//		 DI for database 	
		bind(classOf[ProductRepository])
			.to(classOf[DBProductRepository])
		
		bind(classOf[IProductService])
			.to(classOf[ProductService])
	}
}