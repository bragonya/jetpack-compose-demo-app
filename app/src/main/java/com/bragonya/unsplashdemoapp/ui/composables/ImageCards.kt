package com.bragonya.unsplashdemoapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bragonya.unsplashdemoapp.R
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.ui.SharedViewModel
import com.bragonya.unsplashdemoapp.utils.Fakes
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ItemList(
    image: ImageRoot,
    isFavorite: Boolean,
    isFavCallback: (value: Boolean) -> Unit
) {

        CardImage(
            image,
            isFavorite,
            isFavCallback
        )
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
                    FavoriteButton(
                        isChecked = isFav,
                        onClick = isFavCallback
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

@Preview
@Composable
fun DefaultPreview(){
    CardImage(Fakes.getDummyUser(), false) { value ->  println(value)}
}