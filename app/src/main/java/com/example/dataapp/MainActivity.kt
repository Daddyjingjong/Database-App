package com.example.dataapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal  var dbHelper = SQLiteConfig(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        listen to a click even on the add data image
        imgadd.setOnClickListener {
//            grab data from form
            val name:String = etname.text.trim().toString()
            val profession:String = etprofession.text.trim().toString()
            val residence:String = etresidence.text.trim().toString()
            val password:String = etpassword.text.trim().toString()

            if (name.isEmpty() or profession.isEmpty() or residence.isEmpty()){
                //show message to the user
                show_message("Missing Data","Please fill in all the fields")
            }else{
                  dbHelper.insertData(name,residence,profession,password)
                    show_message("Succcess", "Data by the name ${name} has been added successfully")
//                clear the edittexts after successfully adding data into the db
                clearText()
            }
        }
        imgnext.setOnClickListener {
            val intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }
    }
    private fun show_message(title:String,message: String){
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        
        alertDialog.setPositiveButton("OK",DialogInterface.OnClickListener{dialog, which ->dialog.dismiss()  })
        alertDialog.create().show()
    }
    private fun clearText(){
        etname.setText("")
        etprofession.setText("")
        etresidence.setText("")
        etpassword.setText("")
    }
}
