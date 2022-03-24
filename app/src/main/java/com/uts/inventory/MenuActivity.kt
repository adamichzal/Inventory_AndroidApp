package com.uts.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    lateinit var btnInventory : Button
    lateinit var btnAdmin : Button
    lateinit var btnHelp : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        btnInventory = findViewById(R.id.buttonInventory)
        btnAdmin = findViewById(R.id.buttonAdmin)
        btnHelp = findViewById(R.id.buttonHelp)

        btnInventory.setOnClickListener{
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        btnAdmin.setOnClickListener{
            var i = Intent(this,AdminActivity::class.java)
            startActivity(i)
        }

        btnHelp.setOnClickListener{
            var i = Intent(this,HelpActivity::class.java)
            startActivity(i)
        }
    }
}