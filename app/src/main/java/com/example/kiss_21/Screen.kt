package com.example.kiss_21

sealed class Screen(val route: String) {
    object MainScreen : Screen("mainHomeScreen")
    object DetailsScreen : Screen("detailsScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}