package com.devom.pandit.app

import com.devom.pandit.app.theme.greenColor
import com.devom.pandit.app.theme.greenColorLight
import com.devom.pandit.app.theme.primaryColor
import com.devom.pandit.app.theme.secondaryColor
import com.devom.pandit.app.theme.yellowColor

const val EMPTY = ""
const val ACCESS_TOKEN_KEY = "locallySavedAccessTokenKey"
const val NOTIFICATION_PERMISSION_GRANTED = "notificationPermissionGranted"
const val UNREAD_NOTIFICATION = "unreadNotification"

const val REFRESH_TOKEN_KEY = "locallySavedRefreshTokenKey"
const val UUID_KEY = "uuid"
const val APPLICATION_ID = "applicationId"

const val IMAGE_BASE_URL = "https://devom.blr1.digitaloceanspaces.com/uploads/"
const val DOCUMENT_BASE_URL = "https://devom.blr1.digitaloceanspaces.com/uploads/documents/"
const val BASE_URL = "https://api.devom.co.in"
const val ASSET_LINK_BASE_URL = "https://devom.co.in/pandit/"

val RatingColors = listOf(
    greenColor,
    greenColorLight,
    yellowColor,
    primaryColor,
    secondaryColor
).reversed()
