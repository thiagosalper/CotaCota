package br.com.salperdev.cotacota.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.salperdev.cotacota.data.repository.Fornecedor
import java.util.UUID

@Entity(tableName = "fornecedores")
data class FornecedorEntity(
    @PrimaryKey var fornecedorId: String,
    var name: String = "",
    var phone: String = "",
) {
    fun toFornecedor(): Fornecedor {
        return Fornecedor(
            fornecedorId = fornecedorId,
            name = name,
            phone = phone
        )
    }
}

fun Fornecedor.toFornecedorEntity(): FornecedorEntity {
    return FornecedorEntity(
        fornecedorId = fornecedorId ?: UUID.randomUUID().toString(),
        name = name,
        phone = phone
    )
}
