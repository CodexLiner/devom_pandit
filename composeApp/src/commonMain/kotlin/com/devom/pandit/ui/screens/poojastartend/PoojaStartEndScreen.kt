package com.devom.pandit.ui.screens.poojastartend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.devom.pandit.theme.bgColor
import com.devom.pandit.theme.greyColor
import com.devom.pandit.theme.text_style_h2
import com.devom.pandit.theme.text_style_h5
import com.devom.pandit.theme.text_style_lead_text
import com.devom.pandit.ui.components.ButtonPrimary
import com.devom.pandit.ui.screens.booking.BookingViewModel
import com.devom.models.slots.GetBookingsResponse
import com.devom.pandit.theme.text_style_h4
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pandijtapp.composeapp.generated.resources.Res
import pandijtapp.composeapp.generated.resources.amount_paid
import pandijtapp.composeapp.generated.resources.amount_to_collect
import pandijtapp.composeapp.generated.resources.cancel
import pandijtapp.composeapp.generated.resources.cash_payment_required
import pandijtapp.composeapp.generated.resources.collect_finish_booking
import pandijtapp.composeapp.generated.resources.collect_payment
import pandijtapp.composeapp.generated.resources.collect_payment_note
import pandijtapp.composeapp.generated.resources.finish_booking
import pandijtapp.composeapp.generated.resources.ic_success
import pandijtapp.composeapp.generated.resources.no_payment_required
import pandijtapp.composeapp.generated.resources.noto_money_bag
import pandijtapp.composeapp.generated.resources.paid_online_note
import pandijtapp.composeapp.generated.resources.payment_received

@Composable
fun PoojaStartEndScreen(
    navHostController: NavHostController,
    booking: GetBookingsResponse?,
    otp: String?,
) {
    val viewModel: BookingViewModel = viewModel { BookingViewModel() }

    val isPaid = booking?.isPaid == 1

    val titleText =
        stringResource(if (isPaid) Res.string.payment_received else Res.string.collect_payment)
    val description =
        stringResource(if (isPaid) Res.string.paid_online_note else Res.string.cash_payment_required)
    val buttonText =
        stringResource(if (isPaid) Res.string.amount_paid else Res.string.amount_to_collect)
    val imageResource =
        painterResource(if (isPaid) Res.drawable.noto_money_bag else Res.drawable.ic_success)
    val amount = "₹${(booking?.totalAmount ?: 0f)}"
    val actionButtonText =
        stringResource(if (isPaid) Res.string.finish_booking else Res.string.collect_finish_booking)
    val actionButtonHint =
        stringResource(if (isPaid) Res.string.no_payment_required else Res.string.collect_payment_note)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = imageResource,
                    contentDescription = "Success",
                    modifier = Modifier.size(96.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                textAlign = TextAlign.Center,
                text = titleText,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                textAlign = TextAlign.Center,
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .background(color = bgColor, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, greyColor.copy(alpha = 0.24f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 41.dp, vertical = 20.dp)
            ) {
                Text(
                    minLines = 1,
                    text = buttonText,
                    style = text_style_lead_text,
                    color = Color.Gray
                )

                Text(
                    minLines = 1,
                    text = amount,
                    style = text_style_h5,
                    color = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = actionButtonHint,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            ButtonPrimary(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                buttonText = actionButtonText,
                onClick = {
                    viewModel.updatePoojaStatus(
                        otp =otp.orEmpty(),
                        id = booking?.bookingId ?: 0,
                        type = "end",
                        onResult = {
                            navHostController.navigateUp()
                        }
                    )
                },
                fontStyle = text_style_lead_text
            )

//            ButtonPrimary(
//                fontStyle = text_style_lead_text,
//                buttonText = stringResource(Res.string.cancel),
//                textColor = Color.Black,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.White,
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp)
//                    .border(1.dp, color = greyColor.copy(0.2f), shape = RoundedCornerShape(12.dp))
//            ) {
//                navHostController.navigateUp()
//            }
        }
    }
}
