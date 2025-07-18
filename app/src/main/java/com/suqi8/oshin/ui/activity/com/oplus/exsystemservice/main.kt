package com.suqi8.oshin.ui.activity.com.oplus.exsystemservice

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suqi8.oshin.R
import com.suqi8.oshin.ui.activity.funlistui.FunPage
import com.suqi8.oshin.ui.activity.funlistui.FunSwich
import com.suqi8.oshin.utils.GetAppName
import top.yukonga.miuix.kmp.basic.Card

@SuppressLint("SuspiciousIndentation")
@Composable
fun exsystemservice(navController: NavController) {
    FunPage(
        title = GetAppName(packageName = "com.oplus.exsystemservice"),
        appList = listOf("com.oplus.exsystemservice"),
        navController = navController
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp, top = 6.dp)
        ) {
            FunSwich(
                title = stringResource(R.string.remove_system_tamper_warning),
                category = "exsystemservice",
                key = "remove_system_tamper_warning"
            )
        }
    }
}
