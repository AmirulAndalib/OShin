package com.suqi8.oshin.ui.activity.com.oplus.appdetail

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
import com.suqi8.oshin.ui.activity.funlistui.addline
import com.suqi8.oshin.utils.GetAppName
import top.yukonga.miuix.kmp.basic.Card

@SuppressLint("SuspiciousIndentation")
@Composable
fun appdetail(navController: NavController) {
    FunPage(
        title = GetAppName(packageName = "com.oplus.appdetail"),
        appList = listOf("com.oplus.appdetail"),
        navController = navController
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp, top = 6.dp)
        ) {
            FunSwich(
                title = stringResource(R.string.remove_recommendations),
                category = "appdetail",
                key = "remove_recommendations"
            )
            addline()
            FunSwich(
                title = stringResource(R.string.remove_installation_frequency_popup),
                category = "appdetail",
                key = "remove_installation_frequency_popup"
            )
            addline()
            FunSwich(
                title = stringResource(R.string.remove_attempt_installation_popup),
                category = "appdetail",
                key = "remove_attempt_installation_popup"
            )
            addline()
            FunSwich(
                title = stringResource(R.string.remove_version_check),
                category = "appdetail",
                key = "remove_version_check"
            )
            addline()
            FunSwich(
                title = stringResource(R.string.remove_security_check),
                category = "appdetail",
                key = "remove_security_check"
            )
        }
    }
}
