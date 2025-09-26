package com.example.myapp.features.dollar.domain.usecase

import com.example.myapp.features.dollar.domain.model.DollarModel
import com.example.myapp.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class CambioTipoDollarUseCase(
    val repository: IDollarRepository
) {
    suspend fun invoke(): Flow<DollarModel> {
        return repository.getDollar()
    }
}