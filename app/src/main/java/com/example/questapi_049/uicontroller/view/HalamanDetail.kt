package com.example.questapi_049.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
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
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.detailUiState
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = "Detail Siswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (uiState is DetailUiState.Success) {
                        navigateToEditItem(uiState.siswa.id)
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Siswa"
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        BodyDetailSiswa(
            detailUiState = uiState,
            // Fungsi untuk reload data jika error (retry)
            retryAction = { viewModel.getSatuSiswa() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                deleteConfirmationRequired = true
            }
        )

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    viewModel.deleteSiswa()
                    deleteConfirmationRequired = false
                    navigateBack()
                },
                onDeleteCancel = {
                    deleteConfirmationRequired = false
                }
            )
        }
    }
}

@Composable
fun BodyDetailSiswa(
    detailUiState: DetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is DetailUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailUiState.Success -> {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ItemDetailSiswa(
                    siswa = detailUiState.siswa,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedButton(
                    onClick = onDeleteClick,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete")
                }
            }
        }
        is DetailUiState.Error -> {
            // Memanggil OnError dari HalamanHome.kt
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ItemDetailSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ComponentDetailSiswa(judul = "ID", isinya = siswa.id.toString())
            ComponentDetailSiswa(judul = "Nama", isinya = siswa.nama)
            ComponentDetailSiswa(judul = "Alamat", isinya = siswa.alamat)
            ComponentDetailSiswa(judul = "Telpon", isinya = siswa.telpon)
        }
    }
}

@Composable
fun ComponentDetailSiswa(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = judul,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge
        )
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
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        }
    )
}