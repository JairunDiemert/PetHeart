package com.example.petheart

import androidx.lifecycle.ViewModel

class MemoryListViewModel : ViewModel() {

    private val memoryRepository = MemoryRepository.get()
    val memoryListLiveData = memoryRepository.getMemories()

    //REMOVE below (Testing)
    val memories = mutableListOf<Memory>()

    init {
        for(i in 0 until 100){
            val memory = Memory()
            memory.title = "Memory #$i"
            memory.isFavorited = i % 2 == 0
            memories += memory
        }
    }
}