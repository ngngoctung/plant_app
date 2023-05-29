package com.khtn.plant_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListViewAdapter(private val context: Context, private val dataList: List<DataListView>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val data = dataList[position]
        viewHolder.imageListItem.setImageResource(data.lisviewImage)
        viewHolder.txtName.text = data.name
        viewHolder.txtKingdom.text = data.kingdom
        viewHolder.txtFamily.text = data.family
        viewHolder.txtDescription.text = data.description
        return view
    }

    private class ViewHolder(view: View) {
        val imageListItem: ImageView = view.findViewById(R.id.imageView)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtKingdom: TextView = view.findViewById(R.id.txtKingdom)
        val txtFamily: TextView = view.findViewById(R.id.txtFamily)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
    }
}
