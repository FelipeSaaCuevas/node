package BaseDatos.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nombre")
data class nombre (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val Nombre: String,
    val asunto: String,
    val Monto: Int,
)