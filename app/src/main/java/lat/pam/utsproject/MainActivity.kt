package lat.pam.utsproject

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import lat.pam.utsproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityMainBinding
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        authViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                val intent = Intent(this, ListFoodActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.logtxt.text.toString().trim()
            val password = binding.ed3.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.login(username, password)
            }
        }

        // Mengatur onTouchListener untuk visibilitas password
        binding.ed3.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) { // Mengganti ACTION_UP menjadi ACTION_DOWN
                val DRAWABLE_END = 2
                if (event.rawX >= (binding.ed3.right - binding.ed3.compoundDrawables[DRAWABLE_END].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    updatePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    // Fungsi untuk mengubah visibilitas password
    private fun updatePasswordVisibility() {
        if (isPasswordVisible) {
            binding.ed3.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ed3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0)
        } else {
            binding.ed3.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ed3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0)
        }
        binding.ed3.setSelection(binding.ed3.text.length) // Memastikan kursor tetap di akhir teks
    }
}
