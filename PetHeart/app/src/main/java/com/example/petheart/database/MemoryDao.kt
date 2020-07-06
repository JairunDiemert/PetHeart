package com.example.petheart.database

import androidx.room.Dao
import androidx.room.Query
import com.example.petheart.Memory
import java.util.*

@Dao
interface MemoryDao{

    @Query("SELECT * FROM memory")
    fun getMemories(): List<Memory>

    @Query("SELECT * FROM memory WHERE id=(:id)")
    fun getMemory(id: UUID): Memory?

}