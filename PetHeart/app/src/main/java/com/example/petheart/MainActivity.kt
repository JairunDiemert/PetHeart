package com.example.petheart

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MemoryListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            //val fragment = MemoryFragment()
            val fragment = MemoryListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onMemorySelected(memoryId: UUID){
        val intent = DetailsActivity.newIntent(this, memoryId)
        startActivity(intent)
    }
}