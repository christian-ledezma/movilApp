package com.example.myapp.features.dollar.domain.model

data class DollarModel(
    val tipoCambioOficial: String? = null,
    val tipoCambioParalelo: String? = null,
    val tipoCambioUSDT: String? = null,
    val tipoCambioUSDC: String? = null,
    val lastUpdated: Long? = null
)