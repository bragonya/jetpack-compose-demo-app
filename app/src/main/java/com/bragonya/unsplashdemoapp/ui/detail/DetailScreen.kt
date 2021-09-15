package com.bragonya.unsplashdemoapp.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bragonya.unsplashdemoapp.models.ImageRoot

@Composable
fun DetailView(){
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = "Test Text Now!")
        }
    }
}


@Preview
@Composable
fun PreviewDetailScreen(){
    DetailView()
}
