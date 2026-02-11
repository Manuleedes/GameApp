package com.lidigu.game.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.multiplatform.webview.web.LoadingState

@Composable
actual fun GameWebView(
    url: String, 
    modifier: Modifier,
    onLoadingStateChanged: (Boolean) -> Unit
) {
    val state = rememberWebViewState(url)
    
    LaunchedEffect(state.loadingState) {
        onLoadingStateChanged(state.loadingState is LoadingState.Loading)
    }

    WebView(
        state = state,
        modifier = modifier
    )
}
