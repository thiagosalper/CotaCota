package br.com.salperdev.cotacota.data.source.local

import br.com.salperdev.cotacota.data.repository.Fornecedor
import br.com.salperdev.cotacota.data.repository.WorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource internal constructor(
    private val fornecedoresDao: FornecedoresDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {
    override fun getFornecedoresFlow(): Flow<WorkResult<List<Fornecedor>>> {
        return fornecedoresDao.observeFornecedores().map {
            WorkResult.Success(it.map { fornecedorEntity -> fornecedorEntity.toFornecedor() })
        }
    }

    override fun getFornecedorFlow(fornecedorId: String): Flow<WorkResult<Fornecedor?>> {
        return fornecedoresDao.observeFornecedorById(fornecedorId).map {
            WorkResult.Success(it?.toFornecedor())
        }
    }

    override suspend fun setFornecedores(fornecedores: List<Fornecedor>) {
        fornecedoresDao.setFornecedores(fornecedores.map { it.toFornecedorEntity() })
    }

    override suspend fun addFornecedor(fornecedor: Fornecedor) {
        fornecedoresDao.insertFornecedor(fornecedor.toFornecedorEntity())
    }

    override suspend fun deleteFornecedor(fornecedorId: String) {
        fornecedoresDao.deleteFornecedorById(fornecedorId)
    }

}