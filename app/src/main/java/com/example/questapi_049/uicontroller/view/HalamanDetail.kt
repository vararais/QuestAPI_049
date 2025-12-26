package com.example.questapi_049.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questapi_049.modeldata.DataSiswa
import com.example.questapi_049.uicontroller.route.DestinasiDetail
import com.example.questapi_049.viewmodel.DetailUiState
import com.example.questapi_049.viewmodel.DetailViewModel
import com.example.questapi_049.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    navigateToEditItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = "Detail Siswa",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val state = viewModel.detailUiState
                    if (state is DetailUiState.Success) {
                        navigateToEditItem(state.dataSiswa.id)
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Siswa")
            }
        }
    ) { innerPadding ->
        DetailStatus(
            detailUiState = viewModel.detailUiState,
            retryAction = { viewModel.getDetailSiswa() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                viewModel.deleteSiswa()
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailStatus(
    detailUiState: DetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> {
            ItemDetailMhs(
                dataSiswa = detailUiState.dataSiswa,
                modifier = modifier.fillMaxWidth(),
                onDeleteClick = onDeleteClick
            )
        }
        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    dataSiswa: DataSiswa,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailMhs(judul = "Nama", isinya = dataSiswa.nama)
            ComponentDetailMhs(judul = "Alamat", isinya = dataSiswa.alamat)
            ComponentDetailMhs(judul = "No. Telpon", isinya = dataSiswa.telpon)

            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Delete")
            }

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDeleteClick()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false }
                )
            }
        }
    }
}

@Composable
fun ComponentDetailMhs(judul: String, isinya: String) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = judul, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Text(text = isinya, style = MaterialTheme.typography.bodyLarge)
        Divider()
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) { Text(text = "Cancel") }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) { Text(text = "Yes") }
        }
    )
}