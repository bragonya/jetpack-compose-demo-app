package com.bragonya.unsplashdemoapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bragonya.unsplashdemoapp.ui.SharedViewModel
import com.bragonya.unsplashdemoapp.ui.detail.DetailView
import com.bragonya.unsplashdemoapp.ui.favorites.FavoritesView
import com.bragonya.unsplashdemoapp.ui.home.HomeView
import com.bragonya.unsplashdemoapp.ui.screens.Screen

val items = listOf(
    Screen.Images,
    Screen.Favorites,
)

@Composable
fun UnsplashApp(sharedViewModel: SharedViewModel){
    MaterialTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach{ screen ->
                        BottomNavigationItem(
                            icon = { Icon(screen.icon ?: Icons.Filled.Dangerous, contentDescription = null) },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    launchSingleTop = true

                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = Screen.Images.route, Modifier.padding(innerPadding) ) {
                composable(Screen.Images.route) { HomeView(sharedViewModel, navController) }
                composable(Screen.Favorites.route) { FavoritesView(sharedViewModel, navController) }
                composable(Screen.Detail.route) { DetailView() }
            }

        }

    }
}