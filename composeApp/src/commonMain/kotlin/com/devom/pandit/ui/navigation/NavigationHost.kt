package com.devom.pandit.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.apollographql.apollo.api.http.internal.urlDecode
import com.devom.pandit.ASSET_LINK_BASE_URL
import com.devom.pandit.ui.navigation.Screens.Biography
import com.devom.pandit.ui.navigation.Screens.BookingDetails
import com.devom.pandit.ui.navigation.Screens.Notifications
import com.devom.pandit.ui.navigation.Screens.ReviewsAndRatings
import com.devom.pandit.ui.screens.addbankaccount.BankAccountScreen
import com.devom.pandit.ui.screens.addslot.CreateSlotScreen
import com.devom.pandit.ui.screens.biography.BiographyScreen
import com.devom.pandit.ui.screens.booking.details.BookingDetailScreen
import com.devom.pandit.ui.screens.dashboard.DashboardScreen
import com.devom.pandit.ui.screens.document.UploadDocumentScreen
import com.devom.pandit.ui.screens.helpandsupport.HelpAndSupportDetailScreen
import com.devom.pandit.ui.screens.helpandsupport.HelpAndSupportScreen
import com.devom.pandit.ui.screens.login.LoginScreen
import com.devom.pandit.ui.screens.notification.NotificationScreen
import com.devom.pandit.ui.screens.otpscreen.VerifyOtpScreen
import com.devom.pandit.ui.screens.poojastartend.PoojaStartEndScreen
import com.devom.pandit.ui.screens.profile.EditProfileScreen
import com.devom.pandit.ui.screens.referandearn.ReferAndEarnScreen
import com.devom.pandit.ui.screens.reviews.ReviewsAndRatingsScreen
import com.devom.pandit.ui.screens.rituals.RitualsScreen
import com.devom.pandit.ui.screens.signup.DocumentUploadScreen
import com.devom.pandit.ui.screens.signup.RegisterMainScreen
import com.devom.pandit.ui.screens.signup.SignupSuccessScreen
import com.devom.pandit.ui.screens.transactions.TransactionDetailsScreen
import com.devom.pandit.ui.screens.transactions.TransactionsScreen
import com.devom.pandit.ui.screens.webview.WebView
import com.devom.pandit.ui.screens.withdraw.WithdrawBalanceScreen
import com.devom.pandit.utils.decodeFromString
import com.devom.models.slots.GetBookingsResponse

@Composable
fun NavigationHost(
    startDestination: String = Screens.Login.path,
    navController: NavHostController,
) {
    NavHost(
        navController = navController, startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) },
    ) {
        composable(Screens.Login.path) {
            LoginScreen(navController)
        }
        composable(
            route = Screens.OtpScreen.path.plus("/{mobileNumber}"),
            arguments = listOf(navArgument("mobileNumber") { type = NavType.StringType })
        ) {
            VerifyOtpScreen(
                navController = navController,
                mobileNumber = it.arguments?.getString("mobileNumber")
            )
        }
        composable(Screens.SignUpSuccess.path) {
            SignupSuccessScreen(navHostController = navController)
        }

        composable(
            route = Screens.Register.path,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "${ASSET_LINK_BASE_URL}referral?phone={phone}&code={code}"
                }
            )
        ) {
            val phone = it.arguments?.getString("phone") ?: ""
            val code = it.arguments?.getString("code") ?: ""
            RegisterMainScreen(navController, phone, code = code)
        }
        composable(Screens.Document.path) {
            DocumentUploadScreen(navController)
        }
        composable(
            route = BookingDetails.path.plus("/{booking}"),
            arguments = listOf(navArgument("booking") { type = NavType.StringType })
        ) {
            BookingDetailScreen(
                navController = navController,
                bookingId = it.arguments?.getString("booking")
            )
        }
        composable(
            route = Screens.Dashboard.path
        ) {
            DashboardScreen(navController)
        }
        composable(Screens.EditProfile.path) {
            EditProfileScreen(navController)
        }
        composable(Screens.CreateSlot.path) {
            CreateSlotScreen(
                navController,
            )
        }
        composable(Notifications.path) {
            NotificationScreen(navController)
        }
        composable(Screens.UploadDocument.path) {
            UploadDocumentScreen(navController)
        }
        composable(ReviewsAndRatings.path) {
            ReviewsAndRatingsScreen(navController = navController)
        }
        composable(Biography.path) {
            BiographyScreen(navController = navController)
        }
        composable(Screens.Rituals.path) {
            RitualsScreen(navController = navController)
        }
        composable(Screens.HelpAndSupport.path) {
            HelpAndSupportScreen(navController = navController)
        }
        composable(Screens.ReferAndEarn.path) {
            ReferAndEarnScreen(navController = navController)
        }
        composable(Screens.Transactions.path) {
            TransactionsScreen(navController = navController)
        }
        composable(Screens.TransactionsDetails.path) {
            TransactionDetailsScreen(
                navController = navController,
            )
        }
        composable(
            route = Screens.HelpAndSupportDetailScreen.path.plus("/{ticketId}"),
            arguments = listOf(navArgument("ticketId") { type = NavType.StringType })
        ) {
            HelpAndSupportDetailScreen(
                navController = navController,
                ticketId = it.arguments?.getString("ticketId") ?: ""
            )
        }
        composable(Screens.BankAccountScreen.path) {
            BankAccountScreen(navController = navController)
        }

        composable(Screens.WithdrawBalanceScreen.path) {
            WithdrawBalanceScreen(navController)
        }

        composable(
            route = Screens.PoojaStartEndScreen.path.plus("/{booking}/{otp}"),
            arguments = listOf(
                navArgument("booking") { type = NavType.StringType },
                navArgument("otp") { type = NavType.StringType }
            )
        ) {
            val booking =
                it.arguments?.getString("booking")?.decodeFromString<GetBookingsResponse>()
            val otp = it.arguments?.getString("otp")
            PoojaStartEndScreen(
                navHostController = navController,
                booking = booking,
                otp = otp
            )
        }

        composable(
            route = Screens.WebView.path.plus("/{url}"),
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) {
            WebView(navHostController = navController, url = it.arguments?.getString("url").orEmpty().urlDecode())
        }
    }
}
