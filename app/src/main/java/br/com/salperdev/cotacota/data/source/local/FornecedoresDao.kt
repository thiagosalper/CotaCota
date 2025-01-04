package br.com.salperdev.cotacota.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FornecedoresDao {

    @Query("SELECT * FROM fornecedores")
    fun observeFornecedores(): Flow<List<FornecedorEntity>>

    @Query("SELECT * FROM fornecedores WHERE fornecedorId = :fornecedorId")
    fun observeFornecedorById(fornecedorId: String): Flow<FornecedorEntity?>

    @Query("SELECT * FROM fornecedores")
    suspend fun getFornecedores(): List<FornecedorEntity>

    @Query("SELECT * FROM fornecedores WHERE fornecedorId = :fornecedorId")
    suspend fun getFornecedorById(fornecedorId: String): FornecedorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFornecedor(fornecedor: FornecedorEntity)

    @Update
    suspend fun updateFornecedor(fornecedor: FornecedorEntity): Int

    @Query("DELETE FROM fornecedores WHERE fornecedorId = :fornecedorId")
    suspend fun deleteFornecedorById(fornecedorId: String): Int

    @Query("DELETE FROM fornecedores")
    suspend fun deleteFornecedores()

    @Transaction
    suspend fun setFornecedores(fornecedores: List<FornecedorEntity>){
        deleteFornecedores()
        fornecedores.forEach { insertFornecedor(it) }
    }
}