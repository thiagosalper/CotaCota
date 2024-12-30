package br.com.salperdev.cotacota.data.repository

import br.com.salperdev.cotacota.data.source.local.LocalDataSource
import br.com.salperdev.cotacota.data.source.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultFornecedorRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioCoroutineDispatcher: CoroutineDispatcher
): FornecedorRepository {
    override fun getFornecedoresFlow(): Flow<WorkResult<List<Fornecedor>>> {
        return localDataSource.getFornecedoresFlow()
    }

    override fun getFornecedorFlow(fornecedorId: String): Flow<WorkResult<Fornecedor?>> {
        return localDataSource.getFornecedorFlow(fornecedorId)
    }

    override suspend fun addFornecedor(fornecedor: Fornecedor) {
        val fornecedorWithId = remoteDataSource.addFornecedor(fornecedor)
        localDataSource.addFornecedor(fornecedorWithId)
    }

    override suspend fun deleteFornecedor(fornecedorId: String) {
        remoteDataSource.deleteFornecedor(fornecedorId)
        localDataSource.deleteFornecedor(fornecedorId)
    }

    override suspend fun refreshFornecedor() {
        val fornecedores = remoteDataSource.getFornecedores()
        localDataSource.setFornecedores(fornecedores)
    }
}