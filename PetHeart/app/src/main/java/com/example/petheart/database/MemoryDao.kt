package com.example.petheart.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petheart.modeling.Memory
import java.util.*


@Dao
interface MemoryDao {

    @Query("SELECT * FROM memory")
    fun getMemories(): LiveData<List<Memory>>

    @Query("SELECT * FROM memory WHERE isFavorited=1")
    fun getFavorites(): LiveData<List<Memory>>

    @Query("SELECT * FROM memory WHERE id=(:id)")
    fun getMemory(id: UUID): LiveData<Memory?>

    @Update
    fun updateMemory(memory: Memory)

    @Delete
    fun deleteMemory(memory: Memory)

    @Insert
    fun addMemory(memory: Memory)
}