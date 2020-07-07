package com.example.petheart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val EXTRA_SELECTED_MEMORY_ID = "com.example.petheart.selected_amount_earned"

class DetailsActivity : AppCompatActivity() {

    private lateinit var memoryId: UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        memoryId = intent.getSerializableExtra(EXTRA_SELECTED_MEMORY_ID) as UUID

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = MemoryFragment.newInstance(memoryId)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                //.addToBackStack(null) MIGHT NOT NEED
                .commit()
        }
    }

    companion object {
        fun newIntent(packageContext: Context, memoryId: UUID): Intent {
            return Intent(packageContext, DetailsActivity::class.java).apply{
                putExtra(EXTRA_SELECTED_MEMORY_ID, memoryId)
            }
        }
    }
}