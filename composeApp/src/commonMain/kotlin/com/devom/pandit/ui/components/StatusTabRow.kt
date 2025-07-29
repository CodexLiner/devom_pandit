package com.devom.pandit.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devom.pandit.theme.blackColor
import com.devom.pandit.theme.primaryColor
import com.devom.pandit.theme.text_style_lead_text

@Composable
fun StatusTabRow(selectedTabIndex: MutableState<Int>, tabs: List<String>) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val tabMinWidth = if (tabs.size > 3) maxWidth / 4 else maxWidth / tabs.size

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            contentColor = Color.Black,
            edgePadding = 0.dp,
            containerColor = Color.White,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                        .height(3.dp),
                    color = Color(0xFFFF6F00)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = if (tabMinWidth != Dp.Unspecified)
                        Modifier.widthIn(min = tabMinWidth)
                    else Modifier,
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index },
                    text = {
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            style = text_style_lead_text,
                            color = if (selectedTabIndex.value == index) primaryColor else blackColor,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
        }
    }
}
