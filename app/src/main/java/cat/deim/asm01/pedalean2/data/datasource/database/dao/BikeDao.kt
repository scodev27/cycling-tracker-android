package cat.deim.asm01.pedalean2.data.datasource.database.dao

import androidx.room.*
import cat.deim.asm01.pedalean2.data.datasource.database.model.BikeEntity

@Dao
interface BikeDao {
    @Query("SELECT * FROM bikes")
    fun getAll(): List<BikeEntity>

    @Query("SELECT * FROM bikes WHERE uuid = :uuid")
    fun getById(uuid: String): BikeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bike: BikeEntity)

    @Update
    fun update(bike: BikeEntity)

    @Query("DELETE FROM bikes WHERE uuid = :uuid")
    fun delete(uuid: String)
}