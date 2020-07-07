package com.example.petheart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = MemoryFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                //.addToBackStack(null) MIGHT NOT NEED
                .commit()
        }
    }

    companion object {
        fun newIntent(packageContext: Context, memoryId: UUID): Intent {
            return Intent(packageContext, DetailsActivity::class.java)
        }
    }
}