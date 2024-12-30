package br.com.salperdev.cotacota

import br.com.salperdev.cotacota.data.repository.Fornecedor

data class FornecedoresListUiState(
    val fornecedores: List<Fornecedor> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
