package com.anya.dogipedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anya.dogipedia.ui.breeds.BreedsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, BreedsFragment.newInstance())
                    .commitNow()
        }
    }
}