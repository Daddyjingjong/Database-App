package com.example.dataapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.alert_dialog_box.view.*

class CustomAdapter(var context: Context, var data:ArrayList<DataItem>): BaseAdapter() {
    internal  var dbHelper = SQLiteConfig(context)


    private class ViewHolder(row:View?){
        var name:TextView
        var profession:TextView
        var residence:TextView
        var password:TextView
        var imgdelete:ImageView
        var imgedit:ImageView

        init {

            this.name = row?.findViewById(R.id.tvname) as TextView
            this.profession = row?.findViewById(R.id.tvprofession) as TextView
            this.residence = row?.findViewById(R.id.tvresidence) as TextView
            this.password = row?.findViewById(R.id.tvpassword) as TextView
            this.imgdelete = row?.findViewById(R.id.imgdelete) as ImageView
            this.imgedit = row?.findViewById(R.id.imgedit) as ImageView

        }
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view:View?
        val viewHolder:ViewHolder
        if (convertView == null){
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.card_item_row,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val dataitem:DataItem = getItem(position) as DataItem
        viewHolder.name.text = dataitem.name
        viewHolder.profession.text = dataitem.profession
        viewHolder.residence.text = dataitem.residence
        viewHolder.password.text = dataitem.password

        val name = dataitem.name
        val profession = dataitem.profession
        val residence = dataitem.residence
        val password = dataitem.password

//        update feature
        viewHolder.imgedit.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            val inflater  = LayoutInflater.from(context)
            val dialogview = inflater.inflate(R.layout.alert_dialog_box,parent,false)
            dialogBuilder.setView(dialogview)


//            populate the edit text with data item
            dialogview.detname.setText(name)
            dialogview.detprofession.setText(profession)
            dialogview.detresidence.setText(residence)
            dialogview.detpassword.setText(password)

//            create the builder on the front face
            dialogBuilder.setTitle("Edit ${name}")
            dialogBuilder.setMessage("Do you want to edit ${dataitem.name} ?")

//            when a user selects Edit
            dialogBuilder.setPositiveButton("Edit",{dialog,which ->
//                pull/grab user data

                val updatename = dialogview.detname.text.toString()
                val updateprofession = dialogview.detprofession.text.toString()
                val updateresidence = dialogview.detresidence.text.toString()
                val updatepassword = dialogview.detpassword.text.toString()

                val cursor = dbHelper.alldata

                while (cursor.moveToNext()){
                    if (name == cursor.getString(1)){
                        dbHelper.updateData(
                            cursor.getString(0),
                            updatename,
                            updateprofession,
                            updateresidence,
                            updatepassword
                        )
                    }
                }
                this.notifyDataSetChanged()
                Toast.makeText(context, "${name} update successfully", Toast.LENGTH_LONG).show()


            })
//            when a user selects edit
            dialogBuilder.setNegativeButton("Cancel",{dialog,which -> dialog.dismiss()})
            dialogBuilder.create().show()
        }


        viewHolder.imgdelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Delete")
            dialogBuilder.setMessage("Confirm delete ${name}")

            dialogBuilder.setPositiveButton("Delete",{dialog, which ->
//                delete data
                dbHelper.deleteData(name)
                val users:ArrayList<DataItem> = java.util.ArrayList()

//                pull data from db
                val cursor = dbHelper.alldata
                if (cursor.count == 0){
                    Toast.makeText(context, "No data available",Toast.LENGTH_LONG).show()
                }else{
                    while (cursor.moveToNext()){
                        users.add(
                            DataItem(
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4)
                            )
                        )
                    }
                    this.notifyDataSetChanged()
                }
            })
            dialogBuilder.setNegativeButton("Cancel",{dialog, which -> dialog.dismiss()})
            dialogBuilder.create().show()
        }

        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }

    fun  getIntID(position: Long):Int{
        return position.toInt()
    }
}