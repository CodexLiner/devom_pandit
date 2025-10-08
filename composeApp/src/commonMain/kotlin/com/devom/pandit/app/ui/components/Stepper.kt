package com.devom.pandit.app.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Stepper(
    steps: List<String>,
    currentStep: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, label ->
            StepItem(
                label = label,
                isCompleted = index < currentStep,
                isCurrent = index == currentStep
            )

            if (index < steps.lastIndex) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color(0xFFE8751A))
                )
            }
        }
    }
}

@Composable
fun StepItem(
    label: String,
    isCompleted: Boolean,
    isCurrent: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCompleted -> Color(0xFFE8751A) // Orange
                        isCurrent -> Color.White
                        else -> Color.Gray
                    },
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
//            when {
//                isCompleted -> Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Completed",
//                    tint = Color.White,
//                    modifier = Modifier.size(14.dp)
//                )
//
//                isCurrent -> Canvas(modifier = Modifier.size(6.dp)) {
//                    drawCircle(color = Color.Black)
//                }
//            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
        )
    }
}
