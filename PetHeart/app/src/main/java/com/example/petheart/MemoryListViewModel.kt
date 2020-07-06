package com.example.petheart

import androidx.lifecycle.ViewModel

class MemoryListViewModel : ViewModel() {

    val memories = mutableListOf<Memory>()

    init {
        for(i in 0 until 100){
            val memory = Memory()
            memory.title = "Memory #$i"
            memory.isFavorite = i % 2 == 0
            memories += memory
        }
    }
}