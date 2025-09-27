package com.example.myapp.features.dollar.data.Respository

import com.example.myapp.features.dollar.datasource.RealTimeRemoteDataSource
import com.example.myapp.features.dollar.domain.model.DollarModel
import com.example.myapp.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DollarRepository(val realTimeRemoteDataSource: RealTimeRemoteDataSource) : IDollarRepository {
    override suspend fun getDollar(): Flow<DollarModel> {


        return realTimeRemoteDataSource.getDollarUpdates()
    }

    override suspend fun updateDollarRates(
        oficial: String?,
        paralelo: String?,
        usdt: String?,
        usdc: String?
    ): Result<Unit> {
        return realTimeRemoteDataSource.updateDollarRates(oficial, paralelo, usdt, usdc)
    }
}