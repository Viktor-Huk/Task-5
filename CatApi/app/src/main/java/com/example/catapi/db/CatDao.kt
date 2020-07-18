package com.example.catapi.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catapi.model.Cat

@Dao
interface CatDao {

    @Query("SELECT * FROM cats_table")
    fun getAllCats(): LiveData<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCats(listOfCats: List<Cat>?)
}