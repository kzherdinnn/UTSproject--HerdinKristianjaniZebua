package lat.pam.utsproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import lat.pam.utsproject.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get order directly from OrderManager
        OrderManager.getCurrentOrder()?.let { order ->
            Log.d("ConfirmationActivity", "Displaying order: ${order.food.name}")
            binding.tvFoodNameValue.text = order.food.name
            binding.tvQuantityValue.text = "${order.quantity} pax"
            binding.tvCustomerNameValue.text = order.customerName
            binding.tvNotesValue.text = order.additionalNotes
        } ?: run {
            Log.e("ConfirmationActivity", "No order found in OrderManager")
        }

        binding.backtoMenu.setOnClickListener {
            // Clear the current order when going back to menu
            OrderManager.setCurrentOrder(null)
            startActivity(
                Intent(this, ListFoodActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }
    }
}