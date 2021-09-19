package com.bragonya.unsplashdemoapp.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun UnsplashTheme(content: @Composable () -> Unit){
    MaterialTheme(
        typography = appTypography,
        content = content
    )
}