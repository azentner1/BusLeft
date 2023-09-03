package com.andrej.busleft.base.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
fun FullscreenLoadingComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.testTag(TEST_TAG_LOADING_INDICATOR), color = Color.Blue)
    }
}

const val TEST_TAG_LOADING_INDICATOR = "loading_indicator"