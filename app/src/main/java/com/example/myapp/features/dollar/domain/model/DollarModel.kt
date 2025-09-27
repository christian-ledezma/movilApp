package com.example.myapp.features.dollar.domain.model

data class DollarModel(
    val tipoCambioOficialCompra: String? = null,
    val tipoCambioParaleloCompra: String? = null,
    val tipoCambioOficialVenta: String? = null,
    val tipoCambioPareleloVenta: String? = null,
    val lastUpdated: Long? = null
)