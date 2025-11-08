package ca.tetervak.mathtrainer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.tetervak.mathtrainer.data.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getFirstUser(): UserEntity?

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserById(userId: String): UserEntity?
}