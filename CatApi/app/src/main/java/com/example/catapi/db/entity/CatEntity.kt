package com.example.catapi.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_table")
data class CatEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "url")
    val url: String
)