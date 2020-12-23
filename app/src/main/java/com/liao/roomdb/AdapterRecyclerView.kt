package com.liao.roomdb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.roomdb.database.MyDataClass
import kotlinx.android.synthetic.main.new_row.view.*

class AdapterRecyclerView(private var context: Context, private var list: List<MyDataClass>) :
    RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>() {
    var interactListener: MyAdapterListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.new_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun refreshList(mList: List<MyDataClass>) {
        list = mList
        notifyDataSetChanged()
    }

    interface MyAdapterListener {
        fun deleteItem(myDataClass: MyDataClass, itemView: View)
        fun updateItem(myDataClass: MyDataClass, itemView: View)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(myDataClass: MyDataClass) {
            itemView.text_view_id.text = myDataClass.id.toString()
            itemView.text_view_name.text = myDataClass.name
            interactListener?.deleteItem(myDataClass, itemView)
            interactListener?.updateItem(myDataClass, itemView)

        }
    }
}