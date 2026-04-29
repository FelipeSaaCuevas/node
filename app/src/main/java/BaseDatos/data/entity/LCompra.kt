package BaseDatos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LCompra")
data class LCompra(
    @PrimaryKey(autoGenerate = true)
    val ID: Long = 0,
    val Producto: String,
    val cantida: Int ,

)