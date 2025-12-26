package com.example.questapi_049.viewmodel



sealed interface DetailUiState {
    data class Success(val dataSiswa: DataSiswa) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetail.idArg])

    init {
        getDetailSiswa()
    }