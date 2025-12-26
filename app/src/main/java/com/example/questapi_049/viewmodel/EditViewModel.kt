package com.example.questapi_049.viewmodel

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var editUiState by mutableStateOf(UIStateSiswa())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiEdit.idArg])

    init {
        viewModelScope.launch {
            editUiState = repositoryDataSiswa.getSatuSiswa(_id).toUiStateSiswa(true)
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        editUiState = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    private fun validasiInput(uiState: DetailSiswa = editUiState.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    suspend fun updateSiswa() {
        if (validasiInput(editUiState.detailSiswa)) {
            repositoryDataSiswa.updateDataSiswa(_id, editUiState.detailSiswa.toDataSiswa())
        }
    }
}