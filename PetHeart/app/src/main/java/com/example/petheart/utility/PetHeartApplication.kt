package com.example.petheart.utility

import android.app.Application
import com.example.petheart.repository.MemoryRepository

class PetHeartApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MemoryRepository.initialize(this)
    }
}