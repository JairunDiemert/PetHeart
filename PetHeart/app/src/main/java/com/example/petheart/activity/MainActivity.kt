package com.example.petheart.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petheart.R
import com.example.petheart.fragment.MemoryListFragment
import java.util.*

class MainActivity : AppCompatActivity(), MemoryListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {

            val fragment = MemoryListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onMemorySelected(memoryId: UUID) {
        val intent = DetailsActivity.newIntent(this, memoryId)
        startActivity(intent)
    }
}