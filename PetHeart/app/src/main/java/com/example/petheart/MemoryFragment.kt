package com.example.petheart

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.UUID

private const val TAG = "CrimeFragment"
private const val ARG_MEMORY_ID = "memory_id"

class MemoryFragment : Fragment() {

    private lateinit var memory: Memory
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var favoritedCheckBox: CheckBox
    private val memoryDetailViewModel: MemoryDetailViewModel by lazy{
        ViewModelProviders.of(this).get(MemoryDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memory = Memory()
        val memoryId: UUID =  arguments?.getSerializable(ARG_MEMORY_ID) as UUID
        memoryDetailViewModel.loadMemory(memoryId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memory, container, false)

        titleField = view.findViewById(R.id.memory_title) as EditText
        dateButton = view.findViewById(R.id.memory_date) as Button
        favoritedCheckBox = view.findViewById(R.id.memory_favorited) as CheckBox

        dateButton.apply {
            text = memory.date.toString()
            isEnabled = false
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memoryDetailViewModel.memoryLiveData.observe(
            viewLifecycleOwner,
            Observer{memory->
                memory?.let{
                    this.memory = memory
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                memory.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //This one too
            }
        }

        titleField.addTextChangedListener(titleWatcher)

        favoritedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                memory.isFavorited = isChecked
            }
        }
    }

    private fun updateUI(){
        titleField.setText(memory.title)
        dateButton.text = memory.date.toString()
        favoritedCheckBox.isChecked = memory.isFavorited
    }

    companion object{

        fun newInstance(memoryId: UUID): MemoryFragment {
            val args = Bundle().apply{
                putSerializable(ARG_MEMORY_ID, memoryId)
            }
            return MemoryFragment().apply {
                arguments = args
            }
        }
    }
}