package com.liao.roomdb

import android.R.layout
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.liao.roomdb.database.DatabaseBuilder
import com.liao.roomdb.database.MyDataBase
import com.liao.roomdb.database.MyDataClass
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_row.*
import kotlinx.android.synthetic.main.new_row.view.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), AdapterRecyclerView.MyAdapterListener {
    lateinit var myDataBase: MyDataBase
    var mList = mutableListOf<MyDataClass>()
    lateinit var mAdapter: AdapterRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        insert()
        readAll()

    }

    private fun init() {
        myDataBase = DatabaseBuilder.getInstance(this)
        mAdapter = AdapterRecyclerView(this, mList)
        mAdapter.interactListener = this
        recycler_view.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        floating_button.setOnClickListener {
            Snackbar.make( coor_layout,
                "floating action bar clicked",
                Snackbar.LENGTH_LONG
            ).setAction("Dismiss"
            ) {  }.show()
        }

    }

    private fun readAll() {
        CoroutineScope(Dispatchers.IO).launch {
            mList = myDataBase.myDao().readAll().toMutableList()
            CoroutineScope(Dispatchers.Main).launch {
                mAdapter.refreshList(mList)
            }
        }


    }

    private fun insert() {
        var data = MyDataClass()
        button.setOnClickListener {
            if (edit_text_1.text.isNullOrEmpty()) {
                AlertDialog.Builder(this).setTitle("insert RoomDB error")
                    .setMessage("Primary key id cant be null").setPositiveButton("ok", null).show()
            } else {
                data = MyDataClass(
                    edit_text_1.text.toString().toInt(),
                    edit_text_2.text.toString()
                )
                edit_text_1.setText("")
                edit_text_2.setText("")
//            AlertDialog.Builder(this).setTitle("insert RoomDB")
//                .setMessage("success insert ${data.name}").setPositiveButton("ok", null).show()

                Snackbar.make(
                        coor_layout,
                        "success insert ${data.name}",
                        Snackbar.LENGTH_LONG
                    ).show()

                CoroutineScope(Dispatchers.IO).launch {
                    myDataBase.myDao().addMyDataClass(data)
                    withContext(Dispatchers.Main) {
                        mList.add(data)
                        mAdapter.refreshList(mList)
                    }
                }

            }
        }

    }

    override fun deleteItem(myDataClass: MyDataClass, itemView: View) {
        itemView.button_delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                myDataBase.myDao().delete(myDataClass)
            }
            mList.remove(myDataClass)
            mAdapter.refreshList(mList)
        }
    }

    override fun updateItem(myDataClass: MyDataClass, itemView: View) {
        itemView.button_update.setOnClickListener {
            itemView.button_update.visibility = View.INVISIBLE
            itemView.button_ok.visibility = View.VISIBLE
            button.visibility = View.INVISIBLE
            edit_text_1.isFocusableInTouchMode = false
            edit_text_1.setText(myDataClass.id.toString())
            itemView.button_ok.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    myDataBase.myDao().updateMyDataClass(
                        MyDataClass(
                            myDataClass.id,
                            edit_text_2.text.toString()
                        )
                    )
                    CoroutineScope(Dispatchers.Main).launch {
                        mList[mList.indexOf(myDataClass)] = MyDataClass(
                            myDataClass.id,
                            edit_text_2.text.toString()
                        )
                        mAdapter.refreshList(mList)
                        itemView.button_update.visibility = View.VISIBLE
                        itemView.button_ok.visibility = View.INVISIBLE
                        button.visibility = View.VISIBLE
                        edit_text_1.isFocusableInTouchMode = true
                        edit_text_1.setText("")
                        edit_text_2.setText("")

                    }
                }
            }

        }

    }

}