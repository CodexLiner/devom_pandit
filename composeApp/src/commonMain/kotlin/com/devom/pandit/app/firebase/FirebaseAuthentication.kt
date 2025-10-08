package com.devom.pandit.app.firebase

import com.devom.models.auth.GoogleSignInRequest


expect fun initiateGoogleSignIn(onSignInSuccess: (GoogleSignInRequest) -> Unit = {})