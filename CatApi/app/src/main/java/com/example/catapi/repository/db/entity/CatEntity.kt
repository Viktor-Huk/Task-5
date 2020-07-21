package com.example.catapi.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catapi.model.Cat

@Entity(tableName = "cats_table")
data class CatEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "url")
    val url: String
) {

    fun entityToCat(): Cat {
        return Cat(id, url)
    }

    companion object {
        fun catToEntity(cat: Cat): CatEntity {
            return CatEntity(cat.id, cat.url)
        }
    }
}