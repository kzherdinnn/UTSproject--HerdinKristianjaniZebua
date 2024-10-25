package lat.pam.utsproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<MutableList<Order>>()
    val orders: LiveData<MutableList<Order>> = _orders

    private val _currentOrder = MutableLiveData<Order>()
    val currentOrder: LiveData<Order> = _currentOrder

    init {
        _orders.value = mutableListOf()
        // Load any existing current order
        OrderManager.getCurrentOrder()?.let {
            _currentOrder.value = it
        }
    }

    fun placeOrder(food: Food, quantity: Int, customerName: String, notes: String) {
        val order = Order(food, quantity, customerName, notes)
        val currentOrders = _orders.value ?: mutableListOf()
        currentOrders.add(order)
        _orders.value = currentOrders
        _currentOrder.value = order

        // Store in OrderManager
        OrderManager.setCurrentOrder(order)

        Log.d("OrderViewModel", "Order placed: $order")
    }
}