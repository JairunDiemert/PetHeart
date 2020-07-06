package com.example.petheart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MemoryListFragment"

class MemoryListFragment : Fragment() {

    private val memoryListViewModel: MemoryListViewModel by lazy{
        ViewModelProviders.of(this).get(MemoryListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${memoryListViewModel.memories.size}")
    }

    companion object {
        fun newInstance(): MemoryListFragment {
            return MemoryListFragment()
        }
    }
}