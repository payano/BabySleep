package com.babysleep

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)


        findViewById<Button>(R.id.connect).setOnClickListener {

        }
    }
}