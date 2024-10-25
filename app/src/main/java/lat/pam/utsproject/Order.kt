package lat.pam.utsproject

import java.io.Serializable

data class Order(
    val food: Food,
    val quantity: Int,
    val customerName: String,
    val additionalNotes: String
) : Serializable