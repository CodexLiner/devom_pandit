package com.devom.pandit.firebase

import com.devom.models.auth.GoogleSignInRequest


expect fun initiateGoogleSignIn(onSignInSuccess: (GoogleSignInRequest) -> Unit = {})