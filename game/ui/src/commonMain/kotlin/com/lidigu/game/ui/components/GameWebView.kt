package com.lidigu.game.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun GameWebView(
    url: String, 
    modifier: Modifier = Modifier,
    onLoadingStateChanged: (Boolean) -> Unit = {}
)
