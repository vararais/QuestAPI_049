package com.example.questapi_049.uicontroller.route

import com.example.questapi_049.R

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = R.string.edit_siswa
    const val idSiswa = "id"
    val routeWithArgs = "$route/{$idSiswa}"
}