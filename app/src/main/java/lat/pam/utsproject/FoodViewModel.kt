package lat.pam.utsproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodViewModel : ViewModel() {
    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    private var originalFoodList: List<Food> = listOf() // Menyimpan daftar asli makanan

    init {
        loadFoods()
    }

    private fun loadFoods() {
        val foodList = listOf(
            Food(1, "Batagor", "Batagor asli enak dari Bandung", R.drawable.batagor, 15.0, 4.5), // Gunakan 4.5, bukan 4.5f
            Food(2, "Black Salad", "Salad segar yang dibuat secara langsung", R.drawable.black_salad, 25.0, 4.0), // Gunakan 4.0, bukan 4.0f
            Food(3, "Cappucino", "Kopi cappucino asli yang dibuat dari Kopi Arabica", R.drawable.cappuchino, 18.0, 4.8) // Gunakan 4.8, bukan 4.8f
        )
        // Menyimpan daftar asli makanan
        originalFoodList = foodList // Simpan daftar makanan ke originalFoodList
        _foods.value = foodList
    }



    fun loadAllFoods() {
        // Mengatur ulang _foods ke daftar makanan asli yang disimpan di originalFoodList
        _foods.value = originalFoodList
    }

    fun searchFoods(query: String) {
        // Jika ada pencarian, filter makanan berdasarkan nama atau deskripsi
        val filteredList = originalFoodList.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
        _foods.value = filteredList
    }
}
