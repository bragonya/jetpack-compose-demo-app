package com.bragonya.unsplashdemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bragonya.unsplashdemoapp.common.DEFAULT_PAGE_SIZE
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.repositories.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: UnsplashRepository) : ViewModel() {
    private val internalFavsCache = MutableStateFlow(mapOf<String, ImageRoot>())

    val favsState: StateFlow<Map<String, ImageRoot>> = internalFavsCache

    init {
        viewModelScope.launch {
            internalFavsCache.value = getFavs()
                                            .first()
                                            .map { it.id to it }
                                            .toMap()
        }
    }

    fun getImages() = repository.observeNewsListPaginated(DEFAULT_PAGE_SIZE)

    private fun getFavs() = repository
        .observeFavs()

    fun isFavorite(imageRoot: ImageRoot): Boolean = internalFavsCache.value[imageRoot.id] != null

    fun addFavorite(imageRoot: ImageRoot) = viewModelScope.launch {
        repository.addFavorite(imageRoot)
    }

    fun removeFavorite(imageRoot: ImageRoot) = viewModelScope.launch {
        repository.removeFavorite(imageRoot)
    }

}