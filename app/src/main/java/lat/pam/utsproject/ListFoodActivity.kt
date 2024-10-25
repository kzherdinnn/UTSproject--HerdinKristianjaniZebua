package lat.pam.utsproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import lat.pam.utsproject.databinding.ActivityListFoodBinding

class ListFoodActivity : AppCompatActivity() {
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var binding: ActivityListFoodBinding
    private lateinit var adapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        setupRecyclerView()
        setupSearch()
        observeFoods()
    }

    private fun setupRecyclerView() {
        adapter = FoodAdapter { food ->
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("FOOD_ID", food.id)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSearch() {
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isEmpty()) {
                    // Jika pencarian kosong, tampilkan semua makanan
                    foodViewModel.loadAllFoods() // Pastikan Anda memiliki fungsi ini di ViewModel
                } else {
                    // Jika ada pencarian, filter makanan berdasarkan pencarian
                    foodViewModel.searchFoods(query)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeFoods() {
        foodViewModel.foods.observe(this) { foods ->
            adapter.setFoods(foods)
        }
    }
}
