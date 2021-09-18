package com.bragonya.unsplashdemoapp.ui.detail

import com.bragonya.unsplashdemoapp.models.ImageRoot

sealed class DetailStates {
    class Loading(val isLoading: Boolean): DetailStates()
    class Success(val imageRoot: ImageRoot): DetailStates()
    class Error(val e: Throwable): DetailStates()
}