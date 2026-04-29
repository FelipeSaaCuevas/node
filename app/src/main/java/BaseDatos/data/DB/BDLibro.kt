package BaseDatos.data.DB

import BaseDatos.data.dao.ListaDao
import BaseDatos.data.dao.entityDao
import BaseDatos.data.entity.LCompra
import BaseDatos.data.entity.nombre
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [nombre::class], version = 1)
abstract class BDLibro: RoomDatabase() {
    abstract fun enDao(): entityDao

    companion object{
        fun getDatabase(ctx: Context): BDLibro{
            val db = Room.databaseBuilder( ctx, BDLibro::class.java,"libro").build()
            return db
        }
    }
}
// segunda base de datos para una pagina que registra la lista de compra
@Database(entities = [LCompra::class], version = 1)
abstract  class BDLista: RoomDatabase(){
    abstract fun LisDao(): ListaDao

    companion object{
        fun ListDB(ctx: Context): BDLista{
            val bdlista = Room.databaseBuilder(ctx, BDLista::class.java,"lista").build()
            return bdlista
        }
    }
}