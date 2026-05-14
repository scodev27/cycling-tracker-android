package cat.deim.asm01.pedalean2.data.datasource.database.dao

import androidx.room.*
import cat.deim.asm01.pedalean2.data.datasource.database.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users WHERE uuid = :uuid")
    fun getById(uuid: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Query("DELETE FROM users WHERE uuid = :uuid")
    fun delete(uuid: String)
}