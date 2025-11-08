package ca.tetervak.mathtrainer.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "user_id")
    val uId: String,
    val name: String? = null,
    val email: String? = null,
){
    companion object{
        val demoUser: UserEntity = UserEntity(
                uId = "demo_user",
                name = "Demo User",
                email = "demo.user@example.com"
            )
    }
}

