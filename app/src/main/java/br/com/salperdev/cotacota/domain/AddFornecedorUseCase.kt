package br.com.salperdev.cotacota.domain

import br.com.salperdev.cotacota.data.repository.Fornecedor
import br.com.salperdev.cotacota.data.repository.FornecedorRepository
import javax.inject.Inject

class AddFornecedorUseCase @Inject constructor(private val fornecedorRepository: FornecedorRepository) {
    suspend operator fun invoke(fornecedor: Fornecedor) {
        if (fornecedor.name.isEmpty()) {
            throw Exception("Please specify a fornecedor name")
        }
        if (fornecedor.phone.isEmpty()) {
            throw Exception("Please specify a fornecedor phone")
        }
        fornecedorRepository.addFornecedor(fornecedor)
    }
}