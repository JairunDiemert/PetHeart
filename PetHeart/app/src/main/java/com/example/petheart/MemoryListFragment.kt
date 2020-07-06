package com.example.petheart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MemoryListFragment"

class MemoryListFragment : Fragment() {

    private lateinit var memoryRecyclerView: RecyclerView
    private var adapter: MemoryAdapter? = null

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

        updateUI()

        return view
    }

    private fun updateUI(){
        val memories = memoryListViewModel.memories
        adapter = MemoryAdapter(memories)
        memoryRecyclerView.adapter = adapter
    }

    private inner class MemoryHolder(view:View)
        : RecyclerView.ViewHolder(view){

        val titleTextView: TextView = itemView.findViewById(R.id.memory_title)
        val dateTextView: TextView = itemView.findViewById(R.id.memory_date)
    }

    private inner class MemoryAdapter(var memories: List<Memory>)
        :RecyclerView.Adapter<MemoryHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : MemoryHolder {
            val view = layoutInflater.inflate(R.layout.list_item_memory, parent, false)
            return MemoryHolder(view)
        }

        override fun getItemCount() = memories.size

        override fun onBindViewHolder(holder: MemoryHolder, position: Int) {
            val memory = memories[position]
            holder.apply{
                titleTextView.text = memory.title
                dateTextView.text = memory.date.toString()
            }
        }
    }

    companion object {
        fun newInstance(): MemoryListFragment {
            return MemoryListFragment()
        }
    }
}