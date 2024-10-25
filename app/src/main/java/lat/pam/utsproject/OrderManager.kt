package lat.pam.utsproject

object OrderManager {
    private var currentOrder: Order? = null

    fun setCurrentOrder(order: Order?) {
        currentOrder = order
    }

    fun getCurrentOrder(): Order? = currentOrder
}