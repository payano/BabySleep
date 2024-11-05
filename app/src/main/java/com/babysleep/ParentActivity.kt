package com.babysleep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
    }
}