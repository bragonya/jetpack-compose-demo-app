package com.bragonya.unsplashdemoapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.bragonya.unsplashdemoapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector?) {
    object Images : Screen("images", R.string.images, Icons.Filled.Home)
    object Favorites : Screen("favorites", R.string.favorites, Icons.Filled.Favorite)
    object Detail : Screen("detail/{imageId}", R.string.detail, null)
}