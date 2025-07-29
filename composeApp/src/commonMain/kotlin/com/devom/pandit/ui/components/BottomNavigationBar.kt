package com.devom.pandit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class BottomNavigationScreen(
    val path: String,
    val label: String,
    val icon: DrawableResource,
    val isSelected: Boolean,
)

@Composable
fun BottomMenuBar(
    screens: List<BottomNavigationScreen>,
    selectedIndex: Int = 0,
    onNavigateTo: (Int) -> Unit,
) {
    val backgroundShape = remember { menuBarShape() }

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White, backgroundShape)
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier .padding(vertical = 16.dp).align(Alignment.TopCenter)
        ) {
            val gradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFFE86008), Color(0xFFFFCC00)),
                start = Offset.Zero,
                end = Offset(x = 100f, y = 240f)
            )
            FloatingActionButton(
                shape = RoundedCornerShape(50),
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(0.dp),
                onClick = {
                    onNavigateTo(2)
                },
                modifier = Modifier.background(
                    brush = gradientBrush,
                    shape = RoundedCornerShape(50),
                )
            ) {
                Row(
                    modifier = Modifier.size(64.dp)
                ) {
                    BottomBarItem(
                        allowColorFilter = false,
                        screens = screens,
                        screen = screens[2],
                        showLabel = false,
                        selectedIndex = selectedIndex,
                        onNavigateTo = onNavigateTo
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        Row(
            modifier = Modifier
                .height(56.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomBarItem(screens, screens[0], selectedIndex, onNavigateTo)
            BottomBarItem(screens, screens[1], selectedIndex, onNavigateTo)

            Spacer(modifier = Modifier.width(72.dp))

            BottomBarItem(screens, screens[3], selectedIndex, onNavigateTo)
            BottomBarItem(screens, screens[4], selectedIndex, onNavigateTo)
        }
    }
}


@Composable
fun RowScope.BottomBarItem(
    screens: List<BottomNavigationScreen>,
    screen: BottomNavigationScreen,
    selectedIndex: Int,
    onNavigateTo: (Int) -> Unit,
    showLabel: Boolean = true,
    allowColorFilter: Boolean = true
) {
    val selected = screen == screens[selectedIndex]

    Box(
        Modifier
            .selectable(
                selected = selected,
                onClick = { onNavigateTo(screens.indexOf(screen)) },
                role = Role.Tab,
                interactionSource = null,
                indication = null
            )
            .fillMaxHeight()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        BadgedBox(
            badge = {},
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = painterResource(screen.icon),
                        contentDescription = screen.label,
                        modifier = Modifier.size(24.dp),
                        colorFilter = if (allowColorFilter)
                            ColorFilter.tint(
                                if (selected) Color(0xFFFF6F00) else Color.Gray
                            )
                        else null
                    )
                    if (showLabel) {
                        Text(
                            text = screen.label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selected) Color(0xFFFF6F00) else Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        )
    }
}

private fun menuBarShape() = GenericShape { size, _ ->
    reset()

    moveTo(0f, 0f)

    val width = 150f
    val height = 90f

    val point1 = 75f
    val point2 = 85f

    lineTo(size.width / 2 - width, 0f)

    cubicTo(
        size.width / 2 - point1, 0f,
        size.width / 2 - point2, height,
        size.width / 2, height
    )

    cubicTo(
        size.width / 2 + point2, height,
        size.width / 2 + point1, 0f,
        size.width / 2 + width, 0f
    )

    lineTo(size.width / 2 + width, 0f)

    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)

    close()
}
