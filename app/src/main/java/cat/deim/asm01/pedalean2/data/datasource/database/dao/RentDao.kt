package cat.deim.asm01.pedalean2.data.datasource.database.dao

import androidx.room.*
import cat.deim.asm01.pedalean2.data.datasource.database.model.RentEntity

@Dao
interface RentDao {
    @Query("SELECT * FROM rents")
    fun getAll(): List<RentEntity>

    @Query("SELECT * FROM rents WHERE uuid = :uuid")
    fun getById(uuid: String): RentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rent: RentEntity)

    @Update
    fun update(rent: RentEntity)

    @Query("DELETE FROM rents WHERE uuid = :uuid")
    fun delete(uuid: String)
}