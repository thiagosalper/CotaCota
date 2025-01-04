package br.com.salperdev.cotacota.ui.addEditFornecedor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.salperdev.cotacota.R
import br.com.salperdev.cotacota.data.repository.Fornecedor
import br.com.salperdev.cotacota.data.repository.FornecedorRepository
import br.com.salperdev.cotacota.data.repository.WorkResult
import br.com.salperdev.cotacota.domain.AddFornecedorUseCase
import br.com.salperdev.cotacota.ui.navigation.FornecedoresDestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddEditFornecedorUiState(
    val fornecedorName: String = "",
    val fornecedorPhone: String = "",
    val isLoading: Boolean = false,
    val isFornecedorSaved: Boolean = false,
    val isFornecedorSaving: Boolean = false,
    val fornecedorSavingError: Int? = null
)

@HiltViewModel
class AddEditFornecedorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addFornecedorUseCase: AddFornecedorUseCase,
    private val fornecedorRepository: FornecedorRepository
): ViewModel() {
    private val fornecedorId: String? = savedStateHandle[FornecedoresDestinationsArgs.FORNECEDOR_ID_ARG]

    private val _uiState = MutableStateFlow(AddEditFornecedorUiState())
    val uiState: StateFlow<AddEditFornecedorUiState> = _uiState.asStateFlow()

    init {
        if (fornecedorId != null) {
            loadFornecedor(fornecedorId)
        }
    }

    fun saveFornecedor() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isFornecedorSaving = true) }
                addFornecedorUseCase(
                    Fornecedor(
                        fornecedorId = fornecedorId,
                        name = _uiState.value.fornecedorName,
                        phone = _uiState.value.fornecedorPhone
                    )
                )
                _uiState.update { it.copy(isFornecedorSaved = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(fornecedorSavingError = R.string.error_saving_fornecedor) }
            } finally {
                _uiState.update { it.copy(isFornecedorSaving = false) }
            }
        }
    }

    fun setFornecedorName(name: String) {
        _uiState.update { it.copy(fornecedorName = name) }
    }

    fun setFornecedorPhone(phone: String) {
        _uiState.update { it.copy(fornecedorPhone = phone) }
    }

    private fun loadFornecedor(fornecedorId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = fornecedorRepository.getFornecedorFlow(fornecedorId).first()
            if(result !is WorkResult.Success || result.data == null) {
                _uiState.update { it.copy(isLoading = false) }
            } else {
                val fornecedor = result.data
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        fornecedorName = fornecedor.name,
                        fornecedorPhone = fornecedor.phone
                    )
                }
            }
        }
    }
}