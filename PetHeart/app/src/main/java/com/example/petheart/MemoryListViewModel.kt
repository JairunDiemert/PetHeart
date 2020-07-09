package com.example.petheart

import androidx.lifecycle.ViewModel
import java.io.File

class MemoryListViewModel : ViewModel() {

    private val memoryRepository = MemoryRepository.get()
    val memoryListLiveData = memoryRepository.getMemories()


    fun addMemory(memory: Memory){
        memoryRepository.addMemory(memory)
    }

    fun getPhotoFile(memory: Memory): File {
        return memoryRepository.getPhotoFile(memory)
    }
}