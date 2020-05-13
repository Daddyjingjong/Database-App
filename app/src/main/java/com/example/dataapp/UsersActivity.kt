package com.example.dataapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {
    internal var dbHelper = SQLiteConfig(this)

    @SuppressLint("WrongConstant","Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

//        Add a new user
        adduser.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    imgrefresh.setOnClickListener {
        refresh()
    }
    }

    fun refresh(){
        val users:ArrayList<DataItem> = ArrayList()
        val cursor = dbHelper.alldata
        if (cursor.count == 0){
            show_message("No records","Seems like you have no data")
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
            userlist.adapter = CustomAdapter(this,users)
        }
    }

    private fun show_message(title:String,message: String){
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener{ dialog, which ->dialog.dismiss()  })
        alertDialog.create().show()
    }
}
