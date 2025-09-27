package com.example.myapp.features.dollar.domain.usecase

import com.example.myapp.features.dollar.domain.repository.IDollarRepository

class UpdateDollarRatesUseCase (
    private val repository: IDollarRepository
){
    suspend operator fun invoke(
        oficial: String? = null,
        paralelo: String? = null,
        usdt: String? = null,
        usdc: String? = null
    ): Result<Unit> {
        return repository.updateDollarRates(oficial, paralelo, usdt, usdc)
    }
}