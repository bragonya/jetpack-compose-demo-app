package com.bragonya.unsplashdemoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bragonya.unsplashdemoapp.R
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.utils.Fakes.getDummyUser
import com.skydoves.landscapist.coil.CoilImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                HomeView(homeViewModel)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    CardImage(getDummyUser(), false) { value ->  println(value)}
}

@Composable
fun HomeView(viewModel: HomeViewModel){
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.LightGray
        ) {
            val lazyPages = viewModel.getImages().flow.collectAsLazyPagingItems()
            LazyColumn {
                items(lazyPages){ image ->
                    image?.let {
                        val isFavorite = remember {
                            mutableStateOf(viewModel.isFavorite(it))
                        }
                        CardImage(
                            image,
                            isFavorite.value
                        ) { value ->
                            isFavorite.value = value
                            if (value){
                                viewModel.addFavorite(image)
                            }else {
                                viewModel.removeFavorite(image)
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CardImage(
    imageRoot: ImageRoot,
    isFav: Boolean,
    isFavCallback: (value: Boolean) -> Unit
){
    val image = painterResource(
        if(isFav)
            R.drawable.ic_baseline_favorite_24
        else
            R.drawable.ic_baseline_favorite_border_24
    )
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            CoilImage(
                imageModel = imageRoot.urls.thumbImage,
                contentDescription = "Image showed",
                circularRevealedEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth(0.86f)
                ) {
                    Text(
                        text = imageRoot.description ?: (imageRoot.altDescription ?: ""),
                        style = MaterialTheme.typography.subtitle2
                    )
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = imageRoot.user.profileImage.small,
                                builder = {
                                    transformations(CircleCropTransformation())
                                }
                            ),
                            contentDescription = "profile picture",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = imageRoot.user.name,
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = image,
                        contentDescription = "choose this image a fav",
                        Modifier.clickable(enabled = true) {
                            isFavCallback(!isFav)
                        }
                    )
                    Text(
                        imageRoot.likes.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}