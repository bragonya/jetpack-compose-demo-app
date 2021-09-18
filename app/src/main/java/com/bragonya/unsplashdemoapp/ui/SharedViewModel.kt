package com.bragonya.unsplashdemoapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bragonya.unsplashdemoapp.common.DEFAULT_PAGE_SIZE
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.repositories.UnsplashRepository
import com.bragonya.unsplashdemoapp.ui.detail.DetailStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: UnsplashRepository) : ViewModel() {
    private val internalFavsCache = MutableStateFlow(mapOf<String, ImageRoot>())
    val favsCache =    internalFavsCache.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )

    val imageStateFlow: MutableStateFlow<DetailStates> = MutableStateFlow(DetailStates.Loading(true))

    var job: Job? = null
    init {
         job = viewModelScope.launch {
             getFavs().collect {
                 internalFavsCache.value = it
                     .map { it.id to it }
                     .toMap()
             }

        }
    }

    fun getImages() = repository.observeNewsListPaginated(DEFAULT_PAGE_SIZE)

    fun getImage(imageId: String) = viewModelScope.launch {
        Log.d("bragonyaurl", "$imageId")
        try {
            imageStateFlow.tryEmit(DetailStates.Success(repository.getImage(imageId)))
        }catch (e: Throwable){
            imageStateFlow.tryEmit(DetailStates.Error(e))
        }
    }

    fun getFavs() = repository.observeFavs(viewModelScope)

    fun isFavorite(imageRoot: ImageRoot): Boolean = internalFavsCache.value[imageRoot.id] != null

    fun addFavorite(imageRoot: ImageRoot) = viewModelScope.launch {
        repository.addFavorite(imageRoot)
    }

    fun removeFavorite(imageRoot: ImageRoot) = viewModelScope.launch {
        repository.removeFavorite(imageRoot)
    }

}