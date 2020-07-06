package com.example.petheart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MemoryListFragment"

class MemoryListFragment : Fragment() {

    private lateinit var memoryRecyclerView: RecyclerView

    private val memoryListViewModel: MemoryListViewModel by lazy{
        ViewModelProviders.of(this).get(MemoryListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total memories: ${memoryListViewModel.memories.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memory_list, container, false)

        memoryRecyclerView =
            view.findViewById(R.id.memory_recycler_view) as RecyclerView
        memoryRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    companion object {
        fun newInstance(): MemoryListFragment {
            return MemoryListFragment()
        }
    }
}