package com.example.petheart

import androidx.lifecycle.ViewModel

class MemoryListViewModel : ViewModel() {

    private val memoryRepository = MemoryRepository.get()
    val memoryListLiveData = memoryRepository.getMemories()

    //val memories = mutableListOf<Memory>()

    fun addMemory(memory: Memory){
        memoryRepository.addMemory(memory)
    }

    /*init {
        for(i in 0 until 100){
            val memory = Memory()
            memory.title = "Memory #$i"
            memory.isFavorited = i % 2 == 0
            memories += memory
        }
    }*/
}