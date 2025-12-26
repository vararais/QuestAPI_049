package com.example.questapi_049.uicontroller.route

import com.example.questapi_049.R

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = R.string.detail_siswa
    const val idSiswa = "id"
    val routeWithArgs = "$route/{$idSiswa}"
}