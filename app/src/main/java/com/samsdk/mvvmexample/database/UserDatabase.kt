package com.samsdk.mvvmexample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samsdk.mvvmexample.database.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, UserDatabase::class.java, "user.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}