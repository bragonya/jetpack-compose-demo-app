package com.bragonya.unsplashdemoapp.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bragonya.unsplashdemoapp.R
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.ui.composables.InsetAwareTopAppBar

@Composable
fun DetailView(
    onBack: () -> Unit
){
    val scrollState = rememberLazyListState()
    Scaffold(
        topBar = {
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
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Navigate up",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                elevation = if (!scrollState.isScrolled) 0.dp else 4.dp,
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) { innerPadding ->
        Surface(Modifier.fillMaxSize()) {
            Text(text = "Demo test now!")
        }
    }
}

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0


@Preview
@Composable
fun PreviewDetailScreen(){
    DetailView({})
}
