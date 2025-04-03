package models

import play.api.libs.json._

case class ProductDetail(
	key: String,
	value: String
)

case class Product(
	                  id: Long,
	                  name: String,
	                  category: String,
	                  code: String,
	                  price: BigDecimal,
	                  details: List[ProductDetail]
                  )

object Product {
	implicit val format: OFormat[Product] = Json.format[Product]
	
	// TODO: add validation  	
}

object ProductDetail {
	implicit val format: OFormat[ProductDetail] = Json.format[ProductDetail]
	
	// TODO: add validation  	
}