package com.devom.pandit.ui.screens.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devom.pandit.theme.backgroundColor
import com.devom.pandit.ui.components.AppBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pandijtapp.composeapp.generated.resources.Res
import pandijtapp.composeapp.generated.resources.ic_arrow_left

@Composable
expect fun WebView(
    url: String,
    modifier: Modifier = Modifier.fillMaxSize().background(backgroundColor),
)

@Composable
fun WebView(
    navHostController: NavHostController,
    url: String,
    modifier: Modifier = Modifier.fillMaxSize().background(backgroundColor),
) {
    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        AppBar(
            navigationIcon = painterResource(Res.drawable.ic_arrow_left),
            onNavigationIconClick = {
                navHostController.navigateUp()
            }
        )
        WebView(url = url, modifier = modifier)
    }
}