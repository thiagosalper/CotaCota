package br.com.salperdev.cotacota.data.source.remote

import android.os.SystemClock
import br.com.salperdev.cotacota.data.repository.Fornecedor
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(): RemoteDataSource {
    private val fornecedoresCache = ArrayList<Fornecedor>()
    private var lastDelay = 0L

    override suspend fun getFornecedores(): List<Fornecedor> {
        simulateApiDelay()
        return fornecedoresCache
    }

    override suspend fun addFornecedor(fornecedor: Fornecedor): Fornecedor {
        simulateApiDelay()
        val fornecedorToAdd = if (fornecedor.fornecedorId == null) fornecedor.copy(fornecedorId = UUID.randomUUID().toString()) else fornecedor
        fornecedoresCache.add(fornecedorToAdd)
        return fornecedorToAdd
    }

    override suspend fun deleteFornecedor(fornecedorId: String) {
        simulateApiDelay()
        fornecedoresCache.removeIf { it.fornecedorId == fornecedorId }
    }

    private suspend fun simulateApiDelay() {
        if (SystemClock.uptimeMillis() > lastDelay + 500) {
            delay(2000)
            lastDelay = SystemClock.uptimeMillis()
        }
    }
}