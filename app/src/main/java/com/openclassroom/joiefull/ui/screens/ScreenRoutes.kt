package com.openclassroom.joiefull.ui.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ScreenRoutes (
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
){
    object SplashScreen: ScreenRoutes( route = "splash_screen")
    object CatalogueScreen: ScreenRoutes(
        route = "catalogue_screen",)
    object DetailScreen: ScreenRoutes(
        route = "detail_screen/{productId}",
        navArguments = listOf(navArgument("productId") { type = NavType.IntType }))
}



