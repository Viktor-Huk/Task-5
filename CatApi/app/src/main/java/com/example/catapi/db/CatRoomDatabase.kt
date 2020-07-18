package com.example.catapi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.catapi.db.entity.CatEntity

@Database(entities = [CatEntity::class], version = 1)
abstract class CatRoomDatabase: RoomDatabase() {

    abstract val catDao: CatDao

    companion object {

        @Volatile
        private var INSTANCE: CatRoomDatabase? = null

        fun getDatabase(context: Context): CatRoomDatabase? {

            if (INSTANCE == null) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatRoomDatabase::class.java,
                    "cats_database"
                ).build()

                INSTANCE = instance

                return INSTANCE
            }

            return INSTANCE
        }
    }
}