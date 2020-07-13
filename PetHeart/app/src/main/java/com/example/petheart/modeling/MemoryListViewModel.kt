package com.example.petheart.modeling

import androidx.lifecycle.ViewModel
import com.example.petheart.modeling.Memory
import com.example.petheart.repository.MemoryRepository
import java.io.File

class MemoryListViewModel : ViewModel() {

    private val memoryRepository = MemoryRepository.get()
    val memoryListLiveData = memoryRepository.getMemories()
    val memoryListLiveDataFavorites = memoryRepository.getFavorites()


    fun addMemory(memory: Memory) {
        memoryRepository.addMemory(memory)
    }

    fun getPhotoFile(memory: Memory): File {
        return memoryRepository.getPhotoFile(memory)
    }
}