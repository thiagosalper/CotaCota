package br.com.salperdev.cotacota.ui.fornecedoresList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.salperdev.cotacota.R
import br.com.salperdev.cotacota.data.repository.Fornecedor

@Composable
fun FornecedoresListScreen(
    addFornecedor: () -> Unit,
    editFornecedor: (String) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: FornecedoresListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = addFornecedor) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_fornecedor))
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = viewModel::refreshFornecedoresList) {
                    Text("Refresh")
                }
                Button(onClick = viewModel::addSampleFornecedores) {
                    Text("Add sample fornecedores")
                }

                if(uiState.fornecedores.isEmpty()) {
                    NoFornecedoresInfo()
                } else {
                    FornecedoresList(
                        editFornecedor = editFornecedor,
                        deleteFornecedor = viewModel::deleteFornecedor,
                        fornecedores = uiState.fornecedores
                    )
                }
            }

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Center))
                }
            }
        }
    }

}

@Composable
fun NoFornecedoresInfo() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
        Text(stringResource(R.string.no_fornecedor_label), color = Color.Gray)
    }
}

@Composable
fun FornecedoresList(
    editFornecedor: (String) -> Unit,
    deleteFornecedor: (String) -> Unit,
    fornecedores: List<Fornecedor>
) {
    LazyColumn {
        items(fornecedores) {
            FornecedorItem(
                fornecedor = it,
                onEditFornecedor = editFornecedor,
                onDeleteFornecedor = deleteFornecedor
            )
        }
    }
}


@Composable
fun FornecedorItem(
    fornecedor: Fornecedor,
    onEditFornecedor: (String) -> Unit,
    onDeleteFornecedor: (String) -> Unit
) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable { fornecedor.fornecedorId?.let {onEditFornecedor(it)} }
        .shadow(5.dp, RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xFFBB86FC))
        .padding(10.dp)
    ) {
        Column( modifier = Modifier.weight(1f) ) {
            Text(
                text = fornecedor.name,
                style = MaterialTheme.typography.h4,
            )

            Text("${fornecedor.phone} phone")
        }

        IconButton(
            modifier = Modifier
                .align(CenterVertically)
                .width(32.dp)
                .height(32.dp),
            onClick = {fornecedor.fornecedorId?.let { onDeleteFornecedor(it) }}
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Preview
@Composable
fun FornecedorPreview() {
    FornecedorItem(
        Fornecedor("", "Fornecedor Nome", "19 9 9999 9999"),
        {}
    ) { }
}