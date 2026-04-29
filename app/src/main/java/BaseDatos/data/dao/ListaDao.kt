package BaseDatos.data.dao

import BaseDatos.data.entity.LCompra
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListaDao {

    @Insert
    suspend fun insertar(compra: LCompra)

    @Update
    suspend fun actualizar(compra: LCompra)

    @Delete
    suspend fun eliminar(compra: LCompra)

    @Query("SELECT * FROM LCompra")
    fun obtenerTodos(): LiveData<List<LCompra>>
}