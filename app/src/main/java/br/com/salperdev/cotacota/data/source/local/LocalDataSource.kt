package br.com.salperdev.cotacota.data.source.local

import br.com.salperdev.cotacota.data.repository.Fornecedor
import br.com.salperdev.cotacota.data.repository.WorkResult
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getFornecedoresFlow(): Flow<WorkResult<List<Fornecedor>>>
    fun getFornecedorFlow(fornecedorId: String): Flow<WorkResult<Fornecedor?>>
    suspend fun setFornecedores(fornecedores: List<Fornecedor>)
    suspend fun addFornecedor(fornecedor: Fornecedor)
    suspend fun deleteFornecedor(fornecedorId: String)
}