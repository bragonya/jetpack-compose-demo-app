package com.bragonya.unsplashdemoapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.bragonya.unsplashdemoapp.ui.SharedViewModel
import com.bragonya.unsplashdemoapp.ui.composables.ItemList
import com.bragonya.unsplashdemoapp.ui.composables.SearchView


@Composable
fun FavoritesView(
    viewModel: SharedViewModel,
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray
    ) {
        val lazyPages = viewModel.getFavs().collectAsState(emptyList())
        val searchText = remember { mutableStateOf(TextFieldValue("")) }
        Column {
            SearchView(state = searchText)
            LazyColumn {
                items(lazyPages.value.filter { image ->
                    val text = searchText.value.text
                    (text.isEmpty()
                            || image.description?.contains(text, ignoreCase = true) == true
                            || image.altDescription?.contains(
                        text,
                        ignoreCase = true
                    ) == true) || image.user.name.contains(text, ignoreCase = true)
                }) { image ->
                    val isFavorite = viewModel.isFavorite(image)
                    ItemList(image, isFavorite,
                        isFavCallback = { value ->
                            if (value) {
                                viewModel.addFavorite(image)
                            } else {
                                viewModel.removeFavorite(image)
                            }
                        }) {
                        navController.navigate("detail/${image.id}")
                    }
                }
            }
        }
    }
}