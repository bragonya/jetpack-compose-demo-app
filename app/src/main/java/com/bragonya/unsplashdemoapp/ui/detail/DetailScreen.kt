package com.bragonya.unsplashdemoapp.ui.detail

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
import com.bragonya.unsplashdemoapp.ui.composables.InsetAwareTopAppBar
import com.skydoves.landscapist.coil.CoilImage
import kotlin.math.roundToInt

@Composable
fun DetailView(
    upPress: () -> Unit
) {
    val toolbarHeight = 46.dp

    val scrollState = rememberLazyListState()
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }

    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .background(Color.Gray)
    ) {
        LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
            item {
                CoilImage(
                    imageModel = R.drawable.landscape,
                    contentDescription = "Image showed",
                    circularRevealedEnabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp)),
                    elevation = 4.dp,
                ) {
                    ImageInformation()
                }
            }
        }
        InsetAwareTopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .padding(start = 30.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_android),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(36.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.detail_title, "Igor Bukanov"),
                        style = MaterialTheme.typography.subtitle2,
                        color = LocalContentColor.current,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .weight(1.5f)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = upPress) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigate up",
                        tint = MaterialTheme.colors.primary
                    )
                }
            },
            elevation = if (!scrollState.isScrolled) 0.dp else 4.dp,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
        )
    }
}

@Composable
fun ImageInformation(){
    Column(
        Modifier
            .background(Color.White)
    ) {
        InformationImageCell(dataName = "Name", dataValue = "Peter")
        InformationImageCell(dataName = "Last Name", dataValue = "Godinez")
        InformationImageCell(dataName = "Image Name", dataValue = "Portrait of Suiza")
        InformationImageCell(dataName = "Likes", dataValue = "18")
        InformationImageCell(dataName = "Date", dataValue = "13/11/2020")
    }
}

@Composable
fun InformationImageCell(dataName: String, dataValue: String){
    Divider(color = Color.LightGray, thickness = 1.dp)
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = true) {}
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dataName,
            style = MaterialTheme.typography.caption
        )
        Text(
            text = dataValue,
            style = MaterialTheme.typography.h5
        )
    }
}

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0


@Preview
@Composable
fun PreviewDetailScreen(){
    DetailView({})
}

@Preview
@Composable
fun PreviewImageInformationCell(){
    ImageInformation()
}
