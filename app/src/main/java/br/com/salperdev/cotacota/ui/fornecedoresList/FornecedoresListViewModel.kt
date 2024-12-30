package br.com.salperdev.cotacota.ui.fornecedoresList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.salperdev.cotacota.data.repository.Fornecedor
import br.com.salperdev.cotacota.FornecedoresListUiState
import br.com.salperdev.cotacota.data.repository.FornecedorRepository
import br.com.salperdev.cotacota.data.repository.WorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FornecedoresListViewModel @Inject constructor(
    fornecedorRepository: FornecedorRepository
): ViewModel() {
    private val fornecedores = fornecedorRepository.getFornecedoresFlow()

    val uiState = fornecedores.map { fornecedores ->
        when (fornecedores) {
            is WorkResult.Error -> FornecedoresListUiState(isError = true)
            is WorkResult.Loading -> FornecedoresListUiState(isLoading = true)
            is WorkResult.Success -> FornecedoresListUiState(fornecedores = fornecedores.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FornecedoresListUiState(isLoading = true)
    )

    fun addSampleFornecedores() {
        viewModelScope.launch {
            val fornecedores = arrayOf(
                Fornecedor(name = "Fornecedor UM", phone = "9999"),
                Fornecedor(name = "Fornecedor DOIS", phone = "9999"),
                Fornecedor(name = "Fornecedor TRES", phone = "9999"),
            )

            fornecedores.forEach { addFornecedorUseCase(it) }
        }
    }

    fun deleteFornecedor(fornecedorId: String) {
        viewModelScope.launch {
            fornecedorRepository.deleteFornecedor(fornecedorId)
        }
    }

    fun refreshFornecedoresList() {
        viewModelScope.launch {
            fornecedorRepository.refreshFornecedores()
        }
    }
}