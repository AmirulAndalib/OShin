package com.suqi8.oshin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suqi8.oshin.ui.activity.funlistui.addline
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.extra.SuperArrow
import top.yukonga.miuix.kmp.utils.overScrollVertical

@SuppressLint("UnrememberedMutableState")
@Composable
fun Main_Function(
    topAppBarScrollBehavior: ScrollBehavior,
    navController: NavController,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .overScrollVertical()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) {
        item {
            Spacer(modifier = Modifier.size(padding.calculateTopPadding()))
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 6.dp)
            ) {
                Column {
                    SuperArrow(
                        title = stringResource(id = R.string.cpu_freq_main),
                        onClick = {
                            navController.navigate("func\\cpu_freq")
                        }
                    )
                    addline()
                    SuperArrow(
                        title = stringResource(id = R.string.rom_workshop),
                        onClick = {
                            navController.navigate("func\\romworkshop")
                        }
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.padding(bottom = padding.calculateBottomPadding()))
        }
    }
}
