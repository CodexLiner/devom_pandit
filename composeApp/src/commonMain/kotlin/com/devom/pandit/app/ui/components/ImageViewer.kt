package com.devom.pandit.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devom.pandit.app.models.SupportedFiles
import com.devom.pandit.app.theme.backgroundColor
import com.devom.pandit.app.theme.primaryColor
import com.devom.pandit.app.theme.whiteColor
import com.devom.pandit.app.utils.toDevomDocument
import com.devom.models.pandit.Media
import com.devom.utils.StreamVideo
import org.jetbrains.compose.resources.painterResource
import pandijtapp.composeapp.generated.resources.Res
import pandijtapp.composeapp.generated.resources.ic_close

@Composable
fun ImageViewer(viewImage: MutableState<Boolean>, selectedMedia: MutableState<Media?>) {
    if (viewImage.value) {
        Dialog(
            onDismissRequest = { viewImage.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(400.dp)
                    .background(Color(0xFF121212), RoundedCornerShape(12.dp))
            ) {
                IconButton(
                    onClick = { viewImage.value = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(primaryColor, CircleShape)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = "Close",
                        colorFilter = ColorFilter.tint(whiteColor)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (selectedMedia.value?.documentType == SupportedFiles.VIDEO.type) {
                        selectedMedia.value?.documentUrl.toDevomDocument().StreamVideo()
                    } else {
                        selectedMedia.value?.let { media ->
                            AsyncImage(
                                model = media.documentUrl.toDevomDocument(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }
            }
        }
    }
}
