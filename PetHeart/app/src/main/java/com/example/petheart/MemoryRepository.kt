package com.example.petheart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.petheart.database.MemoryDatabase
import com.example.petheart.database.migration_1_2
import java.io.File
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "memory-database"

class MemoryRepository private constructor(context: Context) {

    private val database: MemoryDatabase = Room.databaseBuilder(
        context.applicationContext,
        MemoryDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_1_2)
        .build()

    private val memoryDao = database.memoryDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir

    //fun getMemories(): List<Memory> = memoryDao.getMemories()
    fun getMemories(): LiveData<List<Memory>> = memoryDao.getMemories()

    //fun getMemory(id: UUID): Memory? = memoryDao.getMemory(id)
    fun getMemory(id: UUID): LiveData<Memory?> = memoryDao.getMemory(id)

    fun updateMemory(memory: Memory){
        executor.execute{
            memoryDao.updateMemory(memory)
        }
    }

    fun addMemory(memory: Memory){
        executor.execute{
            memoryDao.addMemory(memory)
        }
    }

    fun getPhotoFile(memory: Memory): File = File(filesDir, memory.photoFileName)

    companion object {
        private var INSTANCE: MemoryRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MemoryRepository(context)
            }
        }

        fun get(): MemoryRepository {
            return INSTANCE ?: throw IllegalStateException("MemoryRepository must be initialized")
        }
    }
}