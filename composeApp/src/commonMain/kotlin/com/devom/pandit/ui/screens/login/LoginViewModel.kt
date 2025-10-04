package com.devom.pandit.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devom.Project
import com.devom.pandit.ACCESS_TOKEN_KEY
import com.devom.pandit.AuthManager
import com.devom.pandit.REFRESH_TOKEN_KEY
import com.devom.pandit.UUID_KEY
import com.devom.pandit.settings
import com.devom.models.auth.GoogleSignInRequest
import com.devom.models.auth.SaveUserDeviceTokenRequest
import com.devom.network.NetworkClient
import com.devom.network.USER
import com.devom.utils.Application.showToast
import com.devom.utils.network.ResponseResult
import com.devom.utils.network.onResult
import com.russhwolf.settings.set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    init {
        Project.other.clearCacheUseCase.invoke()
    }

    /**
     * send otp on the entered mobile number
     */
    fun sendOtp(mobileNumber: String, onOtpSent: () -> Unit) {
        viewModelScope.launch {
            Project.user.generateOtpUseCase.invoke(mobileNumber).collect {
                it.onResult {
                    onOtpSent()
                    if (mobileNumber == "9039573926") showToast("otp sent successfully ${it.data.otp}")
                }
            }
        }
    }


    fun onGoogleSignIn(request: GoogleSignInRequest) {
        viewModelScope.launch {
            Project.user.googleSignInUseCase.invoke(request).collect { result ->
                result.onResult {
                    settings[ACCESS_TOKEN_KEY] = (result as ResponseResult.Success).data.accessToken
                    settings[REFRESH_TOKEN_KEY] = result.data.refreshToken
                    settings[UUID_KEY] = result.data.uuid
                    settings[USER] = NetworkClient.config.jsonConfig.encodeToString(result.data)
                    AuthManager.login(
                        accessToken = result.data.accessToken,
                        refreshToken = result.data.refreshToken,
                        uuid = result.data.uuid
                    )
                }
            }
        }

    }


    fun saveDeviceToken(token: String = "", device: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Project.user.saveDeviceTokenUseCase.invoke(
                SaveUserDeviceTokenRequest(
                    deviceToken = token,
                    deviceType = device
                )
            ).collect { result ->
                result.onResult {
                    showToast("device token saved successfully")
                }
            }
        }
    }
}