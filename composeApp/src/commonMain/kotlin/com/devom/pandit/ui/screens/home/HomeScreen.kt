package com.devom.pandit.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.devom.pandit.UNREAD_NOTIFICATION
import com.devom.pandit.firebase.MyFirebaseMessagingService
import com.devom.pandit.settings
import com.devom.pandit.theme.backgroundColor
import com.devom.pandit.theme.blackColor
import com.devom.pandit.theme.text_style_h4
import com.devom.pandit.theme.text_style_h5
import com.devom.pandit.theme.whiteColor
import com.devom.pandit.ui.components.AppBar
import com.devom.pandit.ui.components.EarningsBarChart
import com.devom.pandit.ui.components.NoContentView
import com.devom.pandit.ui.navigation.Screens
import com.devom.pandit.ui.screens.booking.components.BookingCard
import com.devom.utils.date.convertIsoToDate
import com.devom.utils.date.toLocalDateTime
import com.russhwolf.settings.get
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.painterResource
import pandijtapp.composeapp.generated.resources.Res
import pandijtapp.composeapp.generated.resources.ic_notification

@Composable
fun HomeScreen(navHostController: NavHostController, onNavigationIconClick: () -> Unit) {
    val viewModel: HomeScreenViewModel = viewModel {
        HomeScreenViewModel()
    }
    val badge = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getBookings()
        viewModel.getTransactions()
        MyFirebaseMessagingService.onNewNotification = {
            badge.value = settings.get<Boolean>(UNREAD_NOTIFICATION) == true
        }
        badge.value = settings.get<Boolean>(UNREAD_NOTIFICATION) == true
    }
    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        AppBar(
            title = "Dashboard", onNavigationIconClick = onNavigationIconClick,
            actions = {
                BadgedBox(
                    badge = {
                        if (badge.value) {
                            Badge(
                                modifier = Modifier.size(10.dp).offset(x = (-4).dp),
                                containerColor = Color.Red
                            )
                        }
                    }) {
                    IconButton(
                        onClick = {
                            navHostController.navigate(Screens.Notifications.path)
                        }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_notification),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        )
        HomeScreenContent(viewModel, navHostController)
    }
}

@Composable
fun HomeScreenContent(viewModel: HomeScreenViewModel, navHostController: NavHostController) {
    val bookings = viewModel.bookings.collectAsState()
    val transactions = viewModel.transactions.collectAsState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 200.dp
        ),
        modifier = Modifier.fillMaxSize().animateContentSize()
    ) {

        item(transactions.value.transactions) {
            EarningsBarChart(transactions = transactions.value.transactions)
        }

        item {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Today's Bookings",
                style = text_style_h5,
                color = blackColor
            )
        }
        val today = Clock.System.now().toLocalDateTime()

        val todayBookings = bookings.value.filter {
            it.bookingDate.convertIsoToDate()?.toLocalDateTime()?.date == today.date
        }

        if (todayBookings.isNotEmpty()) {
            items(todayBookings.take(6)) { booking ->
                BookingCard(
                    booking = booking,
                    onBookingUpdate = {
                        viewModel.updateBookingStatus(booking.bookingId, it)
                    }, onClick = {
                        navHostController.navigate(Screens.BookingDetails.path + "/${booking.bookingId}")
                    }
                )
            }
        } else item {
            Box(modifier = Modifier.fillMaxSize().background(whiteColor , RoundedCornerShape(12.dp)).height(278.dp)) {
                NoContentView(
                    titleTextStyle = text_style_h4,
                    title = "No Bookings Available",
                    message = "You haven’t made any bookings yet. Once you do, they’ll appear here.",
                    image = null
                )
            }
        }
    }
}