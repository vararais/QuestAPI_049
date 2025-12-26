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