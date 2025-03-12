package com.suqi8.oshin.ui.activity.com.android.systemui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.highcapable.yukihookapi.hook.factory.prefs
import com.suqi8.oshin.R
import com.suqi8.oshin.tools.AnimTools
import com.suqi8.oshin.ui.activity.funlistui.FunDropdown
import com.suqi8.oshin.ui.activity.funlistui.FunNoEnable
import com.suqi8.oshin.ui.activity.funlistui.FunPage
import com.suqi8.oshin.ui.activity.funlistui.FunSlider
import com.suqi8.oshin.ui.activity.funlistui.FunString
import com.suqi8.oshin.ui.activity.funlistui.FunSwich
import com.suqi8.oshin.ui.activity.funlistui.addline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.TextField
import top.yukonga.miuix.kmp.extra.SuperArrow
import top.yukonga.miuix.kmp.extra.SuperDropdown

@SuppressLint("RtlHardcoded")
@Composable
fun status_bar_clock(navController: NavController) {
    val context = LocalContext.current
    FunPage(
        title = stringResource(id = R.string.status_bar_clock),
        appList = listOf("com.android.systemui"),
        navController = navController
    ) {
        Column {
            var status_bar_clock by remember { mutableStateOf(context.prefs("systemui\\status_bar_clock").getBoolean("status_bar_clock", false)) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 6.dp, top = 6.dp)
            ) {
                FunSwich(
                    title = stringResource(R.string.status_bar_clock),
                    category = "systemui\\status_bar_clock",
                    key = "status_bar_clock",
                    defValue = false,
                    onCheckedChange = {
                        status_bar_clock = it
                    }
                )
            }
            AnimatedVisibility(
                visible = !status_bar_clock
            ) {
                FunNoEnable()
            }
            val ClockStyleSelectedOption = remember { mutableIntStateOf(context.prefs("systemui\\status_bar_clock").getInt("ClockStyleSelectedOption", 0)) }
            val ClockStyle = listOf(stringResource(R.string.preset), stringResource(R.string.geek))
            AnimatedVisibility(
                visible = status_bar_clock,
                enter = AnimTools().enterTransition(0),
                exit = AnimTools().exitTransition(100)
            ) {
                Column {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        SuperDropdown(
                            title = stringResource(R.string.clock_style),
                            items = ClockStyle,
                            selectedIndex = ClockStyleSelectedOption.intValue,
                            onSelectedIndexChange = { newOption ->
                                ClockStyleSelectedOption.intValue = newOption
                                CoroutineScope(Dispatchers.IO).launch {
                                    context.prefs("systemui\\status_bar_clock").edit { putInt("ClockStyleSelectedOption", newOption) }
                                }
                            }
                        )
                        addline()
                        FunSlider(
                            title = stringResource(R.string.clock_size),
                            summary = stringResource(R.string.clock_size_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ClockSize",
                            defValue = 0f,
                            endtype = "dp",
                            max = 30f,
                            min = 0f,
                            decimalPlaces = 1
                        )
                        addline()
                        FunSlider(
                            title = stringResource(R.string.clock_update_time_title),
                            summary = stringResource(R.string.clock_update_time_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ClockUpdateSpeed",
                            defValue = 0,
                            endtype = "ms",
                            max = 2000f,
                            min = 0f,
                            decimalPlaces = -1
                        )
                    }
                    SmallTitle("dp To px")
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 6.dp)
                    ) {
                        val px = remember { mutableStateOf("0") }
                        TextField(value = px.value, onValueChange = { px.value = it }, modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp))
                        if (px.value.isNotEmpty()) {
                            AnimatedVisibility(visible = px.value.isNotEmpty()) {
                                SmallTitle(text = "${px.value}dp = ${dpToPx(px.value.toFloat(),context)}px")
                            }
                        }
                    }
                    SmallTitle(stringResource(R.string.clock_margin))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 6.dp)
                    ) {
                        FunSlider(
                            title = stringResource(R.string.clock_top_margin),
                            category = "systemui\\status_bar_clock",
                            key = "TopPadding",
                            defValue = 0,
                            endtype = "px",
                            max = 300f,
                            min = 0f,
                            decimalPlaces = 0
                        )
                        addline()
                        FunSlider(
                            title = stringResource(R.string.clock_bottom_margin),
                            category = "systemui\\status_bar_clock",
                            key = "BottomPadding",
                            defValue = 0,
                            endtype = "px",
                            max = 300f,
                            min = 0f,
                            decimalPlaces = 0
                        )
                        addline()
                        FunSlider(
                            title = stringResource(R.string.clock_left_margin),
                            category = "systemui\\status_bar_clock",
                            key = "LeftPadding",
                            defValue = 0,
                            endtype = "px",
                            max = 300f,
                            min = 0f,
                            decimalPlaces = 0
                        )
                        addline()
                        FunSlider(
                            title = stringResource(R.string.clock_right_margin),
                            category = "systemui\\status_bar_clock",
                            key = "RightPadding",
                            defValue = 0,
                            endtype = "px",
                            max = 300f,
                            min = 0f,
                            decimalPlaces = 0
                        )
                    }
                }
            }
            AnimatedVisibility(visible = ClockStyleSelectedOption.intValue == 0 && status_bar_clock) {
                Column {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        FunSwich(
                            title = stringResource(R.string.show_years_title),
                            summary = stringResource(R.string.show_years_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ShowYears",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.show_month_title),
                            summary = stringResource(R.string.show_month_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ShowMonth",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.show_day_title),
                            summary = stringResource(R.string.show_day_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ShowDay",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.show_week_title),
                            summary = stringResource(R.string.show_week_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ShowWeek",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.show_cn_hour_title),
                            summary = stringResource(R.string.show_cn_hour_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ShowCNHour",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.showtime_period_title),
                            summary = stringResource(R.string.showtime_period_summary),
                            category = "systemui\\status_bar_clock",
                            key = "Showtime_period",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.show_seconds_title),
                            summary = stringResource(R.string.show_seconds_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ShowSeconds",
                            defValue = true
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.show_millisecond_title),
                            summary = stringResource(R.string.show_millisecond_summary),
                            category = "systemui\\status_bar_clock",
                            key = "ShowMillisecond",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.hide_space_title),
                            summary = stringResource(R.string.hide_space_summary),
                            category = "systemui\\status_bar_clock",
                            key = "HideSpace",
                            defValue = false
                        )
                        addline()
                        FunSwich(
                            title = stringResource(R.string.dual_row_title),
                            summary = stringResource(R.string.dual_row_summary),
                            category = "systemui\\status_bar_clock",
                            key = "DualRow",
                            defValue = false
                        )
                    }
                }
            }
            AnimatedVisibility(visible = ClockStyleSelectedOption.intValue == 1 && status_bar_clock,
                enter = AnimTools().enterTransition(0),
                exit = AnimTools().exitTransition(100)) {

                Column {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        FunDropdown(
                            title = stringResource(R.string.alignment),
                            category = "systemui\\status_bar_clock",
                            key = "alignment",
                            selectedList = listOf(
                                stringResource(R.string.status_bar_time_gravity_center),
                                stringResource(R.string.status_bar_time_gravity_top),
                                stringResource(R.string.status_bar_time_gravity_bottom),
                                stringResource(R.string.status_bar_time_gravity_end),
                                stringResource(R.string.status_bar_time_gravity_center_horizontal),
                                stringResource(R.string.status_bar_time_gravity_center_vertical),
                                stringResource(R.string.status_bar_time_gravity_fill),
                                stringResource(R.string.status_bar_time_gravity_fill_horizontal),
                                stringResource(R.string.status_bar_time_gravity_fill_vertical)
                            )
                        )
                        addline()
                        FunString(
                            title = stringResource(R.string.clock_format),
                            category = "systemui\\status_bar_clock",
                            key = "CustomClockStyle",
                            defValue = "HH:mm"
                        )
                        addline()
                        SuperArrow(title = stringResource(R.string.clock_format_example), onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "https://oshin.mikusignal.top/docs/timeformat.html".toUri()
                            )
                            context.startActivity(intent)
                        })
                        //SmallTitle(text = stringResource(R.string.status_bar_clock_custom_tips), insideMargin = PaddingValues(18.dp, 8.dp))
                    }
                }
            }
        }
    }
}

fun dpToPx(dp: Float, context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
}
