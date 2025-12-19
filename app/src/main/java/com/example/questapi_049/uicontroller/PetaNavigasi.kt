package com.example.questapi_049.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.questapi_049.uicontroller.route.DestinasiEntry
import com.example.questapi_049.uicontroller.route.DestinasiHome
import com.example.questapi_049.uicontroller.view.EntrySiswaScreen
import com.example.questapi_049.uicontroller.view.HomeScreen

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // Halaman Home
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToUpdate = {
                    // Logika navigasi ke detail/update (belum diimplementasikan di gambar)
                    // Contoh: navController.navigate("${DestinasiDetail.route}/$it")
                }
            )
        }

        // Halaman Entry Siswa
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(
                navigateBack = {
                    // Menggunakan popBackStack agar tumpukan navigasi bersih saat kembali
                    navController.popBackStack()
                }
            )
        }
    }
}
