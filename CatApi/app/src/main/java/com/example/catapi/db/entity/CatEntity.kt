package com.example.catapi.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_table")
data class CatEntity(

    @PrimaryKey val id: String?,
    val url: String?
)