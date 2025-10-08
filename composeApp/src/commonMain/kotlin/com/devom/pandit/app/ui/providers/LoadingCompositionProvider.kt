package com.devom.pandit.app.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalLoaderState = compositionLocalOf { false }

@Composable
fun LoadingCompositionProvider(state: Boolean, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLoaderState provides state) {
        content()
    }
}