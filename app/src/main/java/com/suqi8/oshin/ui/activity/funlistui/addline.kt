package com.suqi8.oshin.ui.activity.funlistui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.highcapable.yukihookapi.hook.factory.prefs
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun addline(mode: Boolean = true) {
    val context = LocalContext.current
    if (context.prefs("settings").getBoolean("addline", false))
        if (mode) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 25.dp, end = 25.dp),
                thickness = 0.5.dp,
                color = MiuixTheme.colorScheme.dividerLine
            )
        } else {
            HorizontalDivider(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                thickness = 0.5.dp,
                color = MiuixTheme.colorScheme.dividerLine
            )
        }
}
