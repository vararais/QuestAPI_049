package com.example.questapi_049.uicontroller.route

import com.example.questapi_049.R

interface DestinasiNavigasi {
    val route: String
    val titleRes: Int
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = R.string.detail_siswa
    const val idArg = "id"
    val routeWithArgs = "$route/{$idArg}"
}

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = R.string.edit_siswa
    const val idArg = "id"
    val routeWithArgs = "$route/{$idArg}"
}