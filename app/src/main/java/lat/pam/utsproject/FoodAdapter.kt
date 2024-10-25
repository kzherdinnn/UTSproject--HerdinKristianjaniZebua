package lat.pam.utsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.bumptech.glide.Glide

class FoodAdapter(private val onFoodClick: (Food) -> Unit) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var foods: List<Food> = listOf()

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ShapeableImageView = view.findViewById(R.id.foodImage)
        val foodName: TextView = view.findViewById(R.id.foodName)
        val foodDescription: TextView = view.findViewById(R.id.foodDescription)
        val foodPrice: TextView = view.findViewById(R.id.foodPrice)
        val foodRating: RatingBar = view.findViewById(R.id.foodRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.foodName.text = food.name
        holder.foodDescription.text = food.description
        holder.foodPrice.text = food.formattedPrice
        holder.foodRating.rating = food.rating.toFloat() // Konversi Double ke Float

        // Menggunakan Glide untuk memuat gambar
        Glide.with(holder.foodImage.context)
            .load(food.imageResId) // Pastikan Anda menggunakan ID sumber yang benar
            .into(holder.foodImage)

        // Set onClickListener for the food item
        holder.itemView.setOnClickListener {
            onFoodClick(food)
        }
    }


    override fun getItemCount() = foods.size

    // Method to update the list of foods
    fun setFoods(foods: List<Food>) {
        this.foods = foods
        notifyDataSetChanged()
    }
}
