package com.samsdk.mvvmexample.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    var id: Int,
    val name: String,
    val userName: String,
    val email: String,
    val phone: String
)
