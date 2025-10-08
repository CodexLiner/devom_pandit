package com.devom.pandit.app.ui.screens.addbankaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.devom.pandit.app.models.SupportedFiles
import com.devom.pandit.app.theme.backgroundColor
import com.devom.pandit.app.ui.components.AppBar
import com.devom.pandit.app.ui.components.AsyncImage
import com.devom.pandit.app.ui.components.ButtonPrimary
import com.devom.pandit.app.ui.components.DocumentPicker
import com.devom.pandit.app.ui.components.TextInputField
import com.devom.pandit.app.utils.toDevomImage
import com.devom.utils.Application
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.source
import kotlinx.io.buffered
import kotlinx.io.readByteArray
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pandijtapp.composeapp.generated.resources.Account_Number
import pandijtapp.composeapp.generated.resources.Add_Bank_Account
import pandijtapp.composeapp.generated.resources.Bank_Name
import pandijtapp.composeapp.generated.resources.Ifsc_code
import pandijtapp.composeapp.generated.resources.Res
import pandijtapp.composeapp.generated.resources.Update
import pandijtapp.composeapp.generated.resources.enter_full_name
import pandijtapp.composeapp.generated.resources.ic_arrow_left

@Composable
fun BankAccountScreen(navController: NavController) {
    val viewModel: BankAccountViewModel = viewModel {
        BankAccountViewModel()
    }
    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        AppBar(
            navigationIcon = painterResource(Res.drawable.ic_arrow_left),
            title = stringResource(Res.string.Add_Bank_Account),
            onNavigationIconClick = { navController.popBackStack() }
        )
        BankAccountScreenContent(navController, viewModel)
    }
}

@Composable
fun ColumnScope.BankAccountScreenContent(
    navController: NavController,
    viewModel: BankAccountViewModel,
) {
    val bankAccount by viewModel.bankAccount.collectAsState()
    var passBookUrl by remember(bankAccount) {
        mutableStateOf(bankAccount.passBookImage.toDevomImage())
    }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        modifier = Modifier.fillMaxWidth().weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TextInputField(
                initialValue = bankAccount.accountHolderName, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ), placeholder = stringResource(Res.string.enter_full_name)
            ) {
                bankAccount.accountHolderName = it
            }
        }

        item {
            TextInputField(
                initialValue = bankAccount.accountNumber, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ), placeholder = stringResource(Res.string.Account_Number)
            ) {
                val filtered = it.filter { it.isDigit() }
                bankAccount.accountNumber = filtered
            }
        }

        item {
            TextInputField(
                initialValue = bankAccount.ifscCode,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ), placeholder = stringResource(Res.string.Ifsc_code)
            ) {
                bankAccount.ifscCode = it
            }
        }

        item {
            TextInputField(
                initialValue = bankAccount.bankName, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                ), placeholder = stringResource(Res.string.Bank_Name)
            ) {
                bankAccount.bankName = it
            }
        }

        item {
            DocumentPicker(
                addIconOnly = passBookUrl.orEmpty().isNotEmpty(),
                modifier = Modifier.padding(vertical = 8.dp),
                allowedDocs = listOf(SupportedFiles.IMAGE),
                title = "Select Bank Proof"
            ) { file, type ->
                passBookUrl = file.path
                bankAccount.file = file.source().buffered().readByteArray()
            }
        }

        item {
            if (passBookUrl?.isNotEmpty() == true) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = passBookUrl.orEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
    ButtonPrimary(
        buttonText = stringResource(Res.string.Update),
        modifier = Modifier.navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp).fillMaxWidth().height(58.dp),
        onClick = {
            val isValid = bankAccount.isValid()
            if (isValid.first) {
                viewModel.updateBankAccount(bankAccount.copy()) {
                    navController.popBackStack()
                }
            } else Application.showToast(isValid.second.orEmpty())
        }
    )
}