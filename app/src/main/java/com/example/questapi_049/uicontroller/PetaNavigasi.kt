package com.example.questapi_049.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.questapi_049.uicontroller.route.DestinasiDetail
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
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetail.route}/$id")
                }
            )
        }
        // Halaman Entry Siswa
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(navigateBack = { navController.navigate(DestinasiHome.route) })
        }
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.idArg) { type = NavType.IntType })
        ) {
            DetailScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEditItem = { id ->
                    navController.navigate("${DestinasiEdit.route}/$id")
                }
            )
        }