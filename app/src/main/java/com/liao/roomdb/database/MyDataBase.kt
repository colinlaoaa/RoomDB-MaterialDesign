package com.liao.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyDataClass::class], version = 1)
abstract class MyDataBase:RoomDatabase() {
    abstract fun myDao():MyDao
}