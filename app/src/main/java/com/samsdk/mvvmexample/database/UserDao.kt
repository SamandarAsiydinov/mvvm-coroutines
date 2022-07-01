package com.samsdk.mvvmexample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samsdk.mvvmexample.database.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUsers(users: List<User>)

    @Query("SELECT * FROM users ORDER BY id DESC")
    suspend fun getAllUsers(): List<User>
}