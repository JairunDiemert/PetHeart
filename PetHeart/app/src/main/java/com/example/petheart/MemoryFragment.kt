package com.example.petheart

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment

class MemoryFragment : Fragment() {

    private lateinit var memory: Memory
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var favoritedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memory = Memory()
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

        dateButton.apply{
            text = memory.date.toString()
            isEnabled = false
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object: TextWatcher {

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
                memory.isFavorite = isChecked
            }
        }
    }
}