package lat.pam.utsproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import lat.pam.utsproject.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var binding: ActivityOrderBinding
    private var selectedFood: Food? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        val foodId = intent.getIntExtra("FOOD_ID", -1)
        loadFoodDetails(foodId)
        setupOrderButton()
    }

    private fun loadFoodDetails(foodId: Int) {
        foodViewModel.foods.observe(this) { foods ->
            selectedFood = foods.find { it.id == foodId }
            selectedFood?.let { food ->
                binding.etFoodName.setText(food.name)
            }
        }
    }

    private fun setupOrderButton() {
        binding.btnOrder.setOnClickListener {
            selectedFood?.let { food ->
                val quantity = binding.etServings.text.toString().toIntOrNull() ?: 0
                val customerName = binding.etName.text.toString()
                val notes = binding.etNotes.text.toString()

                if (validateOrder(quantity, customerName)) {
                    orderViewModel.placeOrder(food, quantity, customerName, notes)
                    Log.d("OrderActivity", "Order placed - Food: ${food.name}, Quantity: $quantity, Name: $customerName")
                    startActivity(Intent(this, ConfirmationActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun validateOrder(quantity: Int, customerName: String): Boolean {
        if (quantity <= 0) {
            Toast.makeText(this, "Please enter a valid quantity", Toast.LENGTH_SHORT).show()
            return false
        }
        if (customerName.isBlank()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}