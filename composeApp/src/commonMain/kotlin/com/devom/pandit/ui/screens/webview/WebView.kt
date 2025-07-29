package com.devom.pandit.ui.screens.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devom.pandit.theme.backgroundColor

@Composable
expect fun WebView(url: String , modifier: Modifier = Modifier.fillMaxSize().background(backgroundColor))