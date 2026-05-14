package cat.deim.asm01.pedalean2.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cat.deim.asm01.pedalean2.data.datasource.database.dao.BikeDao
import cat.deim.asm01.pedalean2.data.datasource.database.dao.RentDao
import cat.deim.asm01.pedalean2.data.datasource.database.dao.UserDao
import cat.deim.asm01.pedalean2.data.datasource.database.model.BikeEntity
import cat.deim.asm01.pedalean2.data.datasource.database.model.RentEntity
import cat.deim.asm01.pedalean2.data.datasource.database.model.UserEntity

@Database(entities = [UserEntity::class, BikeEntity::class, RentEntity::class], version = 1, exportSchema = false)
abstract class Pedalean2AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun bikeDao(): BikeDao
    abstract fun rentDao(): RentDao

    companion object {
        @Volatile
        private var INSTANCE: Pedalean2AppDatabase? = null

        fun getDatabase(context: Context): Pedalean2AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Pedalean2AppDatabase::class.java,
                    "pedalean2_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}