package com.bragonya.unsplashdemoapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bragonya.unsplashdemoapp.ui.SharedViewModel
import com.bragonya.unsplashdemoapp.ui.composables.ItemList
import com.bragonya.unsplashdemoapp.ui.composables.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val favoritesViewModel: SharedViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Favorites(favoritesViewModel)
            }
        }
    }
}

@Composable
fun Favorites(
    viewModel: SharedViewModel
){
    MaterialTheme {
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
                                || image.altDescription?.contains(text, ignoreCase = true) == true) || image.user.name.contains(text, ignoreCase = true)
                    }) { image ->
                        val isFavorite = viewModel.isFavorite(image)
                        ItemList(image, isFavorite){ value ->
                            if (value) {
                                viewModel.addFavorite(image)
                            } else {
                                viewModel.removeFavorite(image)
                            }
                        }
                    }
                }
            }

        }
    }
}