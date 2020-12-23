package com.liao.roomdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_table")
data class MyDataClass(
    @PrimaryKey
    val id: Int? = null,
    val name: String? = null
)
