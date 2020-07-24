package com.example.catapi.repository.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catapi.repository.db.entity.CatEntity

@Database(entities = [CatEntity::class], version = 1)
abstract class CatRoomDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao
}
