package br.com.salperdev.cotacota.data.repository

import kotlinx.coroutines.flow.Flow

interface FornecedorRepository {
    fun getFornecedoresFlow(): Flow<WorkResult<List<Fornecedor>>>
    fun getFornecedorFlow(fornecedorId: String): Flow<WorkResult<Fornecedor?>>
    suspend fun addFornecedor(fornecedor: Fornecedor)
    suspend fun deleteFornecedor(fornecedorId: String)
    suspend fun refreshFornecedor()
}