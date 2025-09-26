package com.example.myapp.features.dollar.domain.repository

import com.example.myapp.features.dollar.domain.model.DollarModel
import kotlinx.coroutines.flow.Flow

interface IDollarRepository {
    suspend fun getDollar() : Flow<com.example.myapp.features.dollar.domain.model.DollarModel>
}