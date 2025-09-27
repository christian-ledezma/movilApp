package com.example.myapp.features.dollar.datasource

import com.example.myapp.features.dollar.domain.model.DollarModel
import com.example.myapp.features.dollar.domain.repository.IDollarRepository
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class RealTimeRemoteDataSource {


    suspend fun getDollarUpdates(): Flow<DollarModel> = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                close(p0.toException())
            }
            override fun onDataChange(p0: DataSnapshot) {
//                val value = p0.getValue(String::class.java)
                val value = p0.getValue(DollarModel::class.java)
                if (value != null) {
                    trySend(value)
                }
            }
        }


//         Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("dollar")
        myRef.addValueEventListener(callback)


        awaitClose {
            myRef.removeEventListener(callback)
        }
    }

    suspend fun updateDollarRates(
        oficial: String? = null,
        paralelo: String? = null,
        usdt: String? = null,
        usdc: String? = null
    ): Result<Unit> {
        return try {
            val database = Firebase.database
            val dollarRef = database.getReference("dollar")

            val updates = mutableMapOf<String, Any>()

            oficial?.let { updates["tipoCambioOficial"] = it }
            paralelo?.let { updates["tipoCambioParalelo"] = it }
            usdt?.let { updates["tipoCambioUSDT"] = it }
            usdc?.let { updates["tipoCambioUSDC"] = it }

            // Agregar timestamp automático de Firebase
            updates["lastUpdated"] = ServerValue.TIMESTAMP

            dollarRef.updateChildren(updates).await()
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
