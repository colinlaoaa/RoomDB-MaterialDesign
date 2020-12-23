package com.liao.roomdb.database

import androidx.room.*

@Dao
interface MyDao {
    @Insert
    fun addMyDataClass(myDataClass: MyDataClass)
    @Query("SELECT * FROM my_table")
    fun readAll():List<MyDataClass>
    @Delete
    fun delete(myDataClass: MyDataClass)
    @Update
    fun updateMyDataClass(myDataClass: MyDataClass)
}


