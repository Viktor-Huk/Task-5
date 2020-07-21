package com.example.catapi.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catapi.model.Cat

@Dao
interface CatDao {

    @Query("SELECT * FROM cats_table")
    fun getAllCats(): List<Cat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCat(cat: List<Cat>)
}