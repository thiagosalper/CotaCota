package br.com.salperdev.cotacota.data.source.remote

import br.com.salperdev.cotacota.data.repository.Fornecedor

interface RemoteDataSource {
    suspend fun getFornecedores(): List<Fornecedor>
    suspend fun addFornecedor(fornecedor: Fornecedor): Fornecedor
    suspend fun deleteFornecedor(fornecedorId: String)
}