package com.example.catapi.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catapi.repository.db.entity.CatEntity

@Dao
interface CatDao {

    @Query("SELECT * FROM cats_table")
    suspend fun getAllCats(): List<CatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCats(cats: List<CatEntity>)
}
