package com.example.petheart

import java.util.*

data class Memory(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isFavorite: Boolean = false
)
