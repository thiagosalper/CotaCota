package br.com.salperdev.cotacota.ui.fornecedoresList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.salperdev.cotacota.data.repository.Fornecedor
import br.com.salperdev.cotacota.data.repository.FornecedorRepository
import br.com.salperdev.cotacota.data.repository.WorkResult
import br.com.salperdev.cotacota.domain.AddFornecedorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FornecedoresListUiState(
    val fornecedores: List<Fornecedor> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

@HiltViewModel
class FornecedoresListViewModel @Inject constructor(
    private val addFornecedorUseCase: AddFornecedorUseCase,
    private val fornecedorRepository: FornecedorRepository
): ViewModel() {
    private val fornecedores = fornecedorRepository.getFornecedoresFlow()
    private val numLoadingItems = MutableStateFlow(0)

    val uiState = combine(fornecedores, numLoadingItems) { fornecedores, loadingItems ->
        when (fornecedores) {
            is WorkResult.Error -> FornecedoresListUiState(isError = true)
            is WorkResult.Loading -> FornecedoresListUiState(isLoading = true)
            is WorkResult.Success -> FornecedoresListUiState(fornecedores = fornecedores.data, isLoading = loadingItems > 0)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FornecedoresListUiState(isLoading = true)
    )

    fun addSampleFornecedores() {
        viewModelScope.launch {
           withLoading {
               val fornecedores = arrayOf(
                   Fornecedor(name = "Fornecedor UM", phone = "9999"),
                   Fornecedor(name = "Fornecedor DOIS", phone = "9999"),
                   Fornecedor(name = "Fornecedor TRES", phone = "9999"),
               )

               fornecedores.forEach { addFornecedorUseCase(it) }
           }
        }
    }

    fun deleteFornecedor(fornecedorId: String) {
        viewModelScope.launch {
            withLoading {
                fornecedorRepository.deleteFornecedor(fornecedorId)
            }
        }
    }

    fun refreshFornecedoresList() {
        viewModelScope.launch {
            withLoading {
                fornecedorRepository.refreshFornecedor()
            }
        }
    }

    private suspend fun withLoading(block: suspend () -> Unit) {
        try {
            addLoadingElement()
            block()
        } finally {
            removeLoadingElement()
        }
    }

    private fun addLoadingElement() = numLoadingItems.getAndUpdate { num -> num + 1 }
    private fun removeLoadingElement() = numLoadingItems.getAndUpdate { num -> num - 1 }
}