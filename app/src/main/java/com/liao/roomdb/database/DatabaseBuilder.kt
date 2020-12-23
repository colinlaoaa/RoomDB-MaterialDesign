package com.liao.roomdb.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: MyDataBase? = null

    fun getInstance(context: Context): MyDataBase {
        if (INSTANCE == null) {
            synchronized(MyDataBase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            MyDataBase::class.java,
            "RoomDB"
        ).build()

}