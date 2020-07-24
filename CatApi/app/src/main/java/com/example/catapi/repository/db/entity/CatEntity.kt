package com.example.catapi.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catapi.model.Cat

@Entity(tableName = "cats_table")
data class CatEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "width")
    val width: Int,

    @ColumnInfo(name = "height")
    val height: Int
) {

    constructor(cat: Cat) : this(cat.id, cat.url, cat.width, cat.height)

    fun entityToCat() = Cat(id, url, width, height)
}
