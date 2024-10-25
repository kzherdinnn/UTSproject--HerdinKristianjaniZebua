package lat.pam.utsproject

// Food.kt - Model class with additional properties
data class Food(
    val id: Int,
    val name: String,
    val description: String,
    val imageResId: Int,
    val price: Double,
    val rating: Double
)
 {
    val formattedPrice: String
        get() = "Rp ${price.toInt()},000"
}
