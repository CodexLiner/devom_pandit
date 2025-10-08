package com.devom.pandit.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devom.pandit.app.theme.backgroundColor

@Composable
fun AppContainer(modifier: Modifier = Modifier.Companion.background(backgroundColor).fillMaxSize(), content: @Composable () -> Unit) {
    Box(modifier = modifier) {
        content()
    }
}