package hello.core.order

data class Order(
    val orderId: Long,
    val itemName: String,
    val itemPrice: Int,
    val discountPrice: Int,
) {
    val calculatePrice = itemPrice - discountPrice
}