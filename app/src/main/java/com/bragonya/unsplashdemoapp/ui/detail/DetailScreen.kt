package com.bragonya.unsplashdemoapp.ui.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bragonya.unsplashdemoapp.R
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.ui.SharedViewModel
import com.bragonya.unsplashdemoapp.ui.composables.FavoriteButton
import com.bragonya.unsplashdemoapp.ui.composables.InsetAwareTopAppBar
import com.skydoves.landscapist.coil.CoilImage
import kotlin.math.roundToInt

@Composable
fun DetailView(
    upPress: () -> Unit,
    imageId: String,
    detailScreenViewModel: SharedViewModel
) {

    detailScreenViewModel.getImage(imageId)


    val image = detailScreenViewModel.imageStateFlow.collectAsState()

    when(val state = image.value){
        is DetailStates.Error -> Text(text = "Error ${state.e}")
        is DetailStates.Loading -> Text(text = "Loading")
        is DetailStates.Success -> SuccessDetailView(upPress, state.imageRoot)
    }


}


