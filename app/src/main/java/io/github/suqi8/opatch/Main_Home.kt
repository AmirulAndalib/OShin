package io.github.suqi8.opatch

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.Build
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.factory.prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import top.yukonga.miuix.kmp.basic.Box
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.LazyColumn
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * 绘制阴影范围
 * [top] 顶部范围
 * [start] 开始范围
 * [bottom] 底部范围
 * [end] 结束范围
 * Create empty Shadow elevation
 */
open class ShadowElevation(
    val top: Dp = 0.dp,
    private val start: Dp = 0.dp,
    private val bottom: Dp = 0.dp,
    private val end: Dp = 0.dp
){
    companion object : ShadowElevation()
}

/**
 * 绘制基础阴影
 * @param color 颜色
 * @param alpha 颜色透明度
 * @param borderRadius 阴影便捷圆角
 * @param shadowRadius 阴影圆角
 * @param offsetX 偏移X轴
 * @param offsetY 偏移Y轴
 * @param roundedRect 是否绘制圆角就行
 */
fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    roundedRect: Boolean = true
) = this.drawBehind {
    /**将颜色转换为Argb的Int类型*/
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = .0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    /**调用Canvas绘制*/
    this.drawIntoCanvas {
        val paint = Paint()
        paint.color = Color.Transparent
        /**调用底层fragment Paint绘制*/
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        /**绘制阴影*/
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        /**形状绘制*/
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            if (roundedRect) this.size.height / 2 else borderRadius.toPx(),
            if (roundedRect) this.size.height / 2 else borderRadius.toPx(),
            paint
        )
    }
}

@Composable
fun addline(mode: Boolean = true) {
    val context = LocalContext.current
    if (context.prefs("settings").getBoolean("addline", false))
        if (mode) {
            HorizontalDivider(modifier = Modifier.padding(start = 25.dp, end = 25.dp), thickness = 0.5.dp, color = Color.Gray.copy(alpha = 0.2f))
        } else {
            HorizontalDivider(modifier = Modifier.padding(start = 5.dp, end = 5.dp), thickness = 0.5.dp, color = Color.Gray.copy(alpha = 0.2f))
        }
    else {}
}

@Composable
fun Main_Home(padding: PaddingValues,topAppBarScrollBehavior: ScrollBehavior) {
    /*val loading = remember { mutableStateOf(true) }
    if (loading.value) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
            Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
        }
    }*/
    LazyColumn(
        contentPadding = PaddingValues(top = padding.calculateTopPadding()),
        topAppBarScrollBehavior = topAppBarScrollBehavior
    ) {
        item {
            val cardVisible = remember { mutableStateOf(false) }
            val cardVisible1 = remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                cardVisible.value = true
                //loading.value = false
            }
            LaunchedEffect(Unit) {
                cardVisible1.value = true
                delay(6000)
                cardVisible1.value = false
            }

            // 卡片1动画
            AnimatedVisibility(
                visible = cardVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { -it }, // 从上方进入
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            ) {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp)
                    .drawColoredShadow(
                        if (YukiHookAPI.Status.isModuleActive) MiuixTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer,
                        1f,
                        borderRadius = 0.dp,
                        shadowRadius = 15.dp,
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        roundedRect = false
                    ),
                    color = if (YukiHookAPI.Status.isModuleActive) MiuixTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                id = if (YukiHookAPI.Status.isModuleActive)
                                    R.drawable.twotone_check_circle_24
                                else R.drawable.twotone_unpublished_24
                            ),
                            contentDescription = null
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(start = 30.dp)
                        ) {
                            Text(
                                text = if (YukiHookAPI.Status.isModuleActive)
                                    stringResource(R.string.module_is_activated)
                                else stringResource(R.string.module_not_activated),
                                color = Color.Black
                            )
                            Text(
                                text = if (YukiHookAPI.Status.isModuleActive)
                                    "${YukiHookAPI.Status.Executor.name}-v${YukiHookAPI.Status.Executor.apiLevel}"
                                else stringResource(R.string.please_activate),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = cardVisible1.value,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    color = Color.Red.copy(alpha = 0.1f)
                ) {
                    top.yukonga.miuix.kmp.basic.BasicComponent(
                        summary = stringResource(R.string.module_notice),
                        summaryColor = Color.Red
                    )
                }
            }

            // 卡片2动画
            AnimatedVisibility(
                visible = cardVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            ) {
                Card(modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
                ) {
                    var nvid by remember { mutableStateOf("0") }

                    val country: String = when (nvid) {
                        "10010111" -> stringResource(R.string.nvid_CN)
                        "00011010" -> stringResource(R.string.nvid_TW)
                        "00110111" -> stringResource(R.string.nvid_RU)
                        "01000100" -> stringResource(R.string.nvid_GDPR_EU)
                        "10001101" -> stringResource(R.string.nvid_GDPR_Europe)
                        "00011011" -> stringResource(R.string.nvid_IN)
                        "00110011" -> stringResource(R.string.nvid_ID)
                        "00111000" -> stringResource(R.string.nvid_MY)
                        "00111001" -> stringResource(R.string.nvid_TH)
                        "00111110" -> stringResource(R.string.nvid_PH)
                        "10000011" -> stringResource(R.string.nvid_SA)
                        "10011010" -> stringResource(R.string.nvid_LATAM)
                        "10011110" -> stringResource(R.string.nvid_BR)
                        "10100110" -> stringResource(R.string.nvid_ME)
                        else -> stringResource(R.string.nvid_unknown, nvid)
                    }
                    var health by remember { mutableStateOf("0") }
                    var versionMessage by remember { mutableStateOf("0") }
                    var ksuVersion by remember { mutableStateOf("0") }
                    var battery_cc: Int by remember { mutableStateOf(0) }
                    var charge_full_design: Int by remember { mutableStateOf(0) }
                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO) {
                            nvid = getSystemProperty("ro.build.oplus_nv_id")
                            health = executeCommand("cat /sys/class/power_supply/battery/health").trim().toString()
                            ksuVersion = executeCommand("/data/adb/ksud -V")
                            versionMessage = if (ksuVersion.isEmpty()) {
                                val magiskVersion = executeCommand("magisk -v")
                                magiskVersion +" "+ executeCommand("magisk -V").trim()
                            } else {
                                val version = ksuVersion.substringAfter("ksud ").substring(0, 4)
                                version
                            }
                            battery_cc = try {
                                executeCommand("cat /sys/class/oplus_chg/battery/battery_cc").trim().toInt()
                            } catch (e: Exception) {0}
                            charge_full_design = try {
                                executeCommand("cat /sys/class/power_supply/battery/charge_full_design").trim().toInt() / 1000
                            } catch (e: Exception) { 0 }
                        }


                    }
                    val batteryHealthString = when (health) {
                        "Good" -> stringResource(R.string.battery_health_good)
                        "Overheat" -> stringResource(R.string.battery_health_overheat)
                        "Dead" -> stringResource(R.string.battery_health_dead)
                        "Over Voltage" -> stringResource(R.string.battery_health_over_voltage)
                        "Cold" -> stringResource(R.string.battery_health_cold)
                        "Unknown" -> stringResource(R.string.battery_health_unknown)
                        else -> stringResource(R.string.battery_health_not_found)
                    }
                    // 使用 MutableState 存储电池信息
                    val currentCapacity = remember { mutableStateOf("0 mAh") }
                    val fullCapacity = remember { mutableStateOf("0 mAh") }
                    val batteryHealth = remember { mutableStateOf("0%") }
                    val lifecycleOwner = LocalLifecycleOwner.current
                    var isForeground = remember { mutableStateOf(false) }
                    DisposableEffect(Unit) {
                        val observer = LifecycleEventObserver { _, event ->
                            when (event) {
                                Lifecycle.Event.ON_START -> {
                                    isForeground.value = true
                                }
                                Lifecycle.Event.ON_STOP -> {
                                    isForeground.value = false
                                }
                                else -> {}
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }
                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO) {
                            while (true) {
                                if (isForeground.value) {
                                    currentCapacity.value = try {
                                        when {
                                            executeCommand("cat /sys/class/oplus_chg/battery/charge_full").isNotEmpty() -> {
                                                (executeCommand("cat /sys/class/oplus_chg/battery/charge_full").trim().toInt() / 1000).toString()
                                            }
                                            executeCommand("cat /sys/class/power_supply/battery/charge_counter").isNotEmpty() -> {
                                                (executeCommand("cat /sys/class/power_supply/battery/charge_counter").trim().toInt() / 1000).toString()
                                            }
                                            executeCommand("cat /sys/class/power_supply/battery/charge_now").isNotEmpty() -> {
                                                (executeCommand("cat /sys/class/power_supply/battery/charge_now").trim().toInt() / 1000).toString()
                                            }
                                            else -> "ERROR"
                                        }
                                    } catch (e: Exception) {
                                        e.message
                                    } + " mAh"
                                    fullCapacity.value = try {
                                        executeCommand("cat /sys/class/oplus_chg/battery/battery_fcc").trim().toInt().toString()
                                    } catch (e: Exception) {e.message} + " mAh"
                                    batteryHealth.value = try {
                                        getSOH() + "% / " +
                                                (executeCommand("cat /sys/class/oplus_chg/battery/battery_fcc").trim().toFloat() /
                                                        (executeCommand("cat /sys/class/power_supply/battery/charge_full_design").trim().toFloat() / 100000)).toString()
                                    } catch (e: Exception) { e.message } + "%"
                                }
                                delay(10000L)
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)) {
                        Text(text = stringResource(id = R.string.countries_and_regions),modifier = Modifier.padding(bottom=5.dp))
                        SmallTitle(text = country, insideMargin = DpSize(0.dp,0.dp),modifier = Modifier.padding(bottom=5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.android_version)+" / "+stringResource(id = R.string.android_api_version),modifier = Modifier.padding(top=5.dp))
                        SmallTitle(text = Build.VERSION.RELEASE+"/"+Build.VERSION.SDK_INT, insideMargin = DpSize(0.dp,0.dp),modifier = Modifier.padding(bottom=5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.battery_status),modifier = Modifier.padding(top=5.dp))
                        SmallTitle(text = batteryHealthString+" / "+health, insideMargin = DpSize(0.dp,0.dp),modifier = Modifier.padding(bottom=5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.system_version),modifier = Modifier.padding(top=5.dp))
                        SmallTitle(text = Build.DISPLAY, insideMargin = DpSize(0.dp,0.dp),modifier = Modifier.padding(bottom=5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.battery_equivalent_capacity),modifier = Modifier.padding(top=5.dp))
                        SmallTitle(text = charge_full_design.toString()+"mAh", insideMargin = DpSize(0.dp,0.dp),modifier = Modifier.padding(bottom=5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.battery_current_capacity), modifier = Modifier.padding(top = 5.dp))
                        SmallTitle(text = currentCapacity.value, insideMargin = DpSize(0.dp, 0.dp), modifier = Modifier.padding(bottom = 5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.battery_full_capacity), modifier = Modifier.padding(top = 5.dp))
                        SmallTitle(text = fullCapacity.value, insideMargin = DpSize(0.dp, 0.dp), modifier = Modifier.padding(bottom = 5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.battery_health), modifier = Modifier.padding(top = 5.dp))
                        SmallTitle(text = batteryHealth.value, insideMargin = DpSize(0.dp, 0.dp), modifier = Modifier.padding(bottom = 5.dp))
                        addline(false)
                        Text(text = stringResource(id = R.string.battery_cycle_count),modifier = Modifier.padding(top=5.dp))
                        SmallTitle(text = battery_cc.toString()+"次", insideMargin = DpSize(0.dp,0.dp),modifier = Modifier.padding(bottom=5.dp))
                        addline(false)
                        Text(text = if (ksuVersion.isEmpty()) stringResource(id = R.string.magisk_version) else stringResource(id = R.string.ksu_version),modifier = Modifier.padding(top=5.dp))
                        SmallTitle(text = versionMessage.trim(), insideMargin = DpSize(0.dp,0.dp),modifier = Modifier.padding(bottom=5.dp))
                    }
                }
            }

            Spacer(Modifier.size(65.dp))
        }
    }

}

@SuppressLint("DefaultLocale")
suspend fun getSOH(): String {
    var soh = executeCommand("cat /sys/class/oplus_chg/battery/battery_soh").trim().toDouble()
    val fcc = executeCommand("cat /sys/class/oplus_chg/battery/battery_fcc").trim().toDouble()

    val getDesignCapacity = executeCommand("cat /sys/class/oplus_chg/battery/design_capacity")
    return when {
        soh < 50 -> {
            val designCapacity = getDesignCapacity // Assume this function exists
            val fccs = fcc * 100
            soh = (fccs.toFloat() / designCapacity.toFloat()).toDouble()
            String.format("%.1f", soh)
        }
        soh > 101 -> {
            val designCapacity = getDesignCapacity // Assume this function exists
            val fccs = fcc * 100
            soh = (fccs.toFloat() / designCapacity.toFloat()).toDouble()
            String.format("%.1f", soh)
        }
        else -> String.format("%.1f", soh)
    }
}

@SuppressLint("PrivateApi")
fun getSystemProperty(name: String): String {
    return try {
        val method = Class.forName("android.os.SystemProperties")
            .getMethod("get", String::class.java)
        method.invoke(null, name) as String
    } catch (e: Exception) {
        "null"
    }
}

suspend fun executeCommand(command: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }

            process.waitFor()
            reader.close()
            output.toString().trim()
        } catch (e: Exception) {
            Log.e(TAG, "executeCommand: $e", )
            return@withContext "0"
        }
    }
}

@Composable
fun GetAppIconAndName(packageName: String, onAppInfoLoaded: @Composable (String, ImageBitmap) -> Unit) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val applicationInfo = remember { mutableStateOf<android.content.pm.ApplicationInfo?>(null) }

    LaunchedEffect(packageName) {
        try {
            applicationInfo.value = packageManager.getApplicationInfo(packageName, 0)
        } catch (_: PackageManager.NameNotFoundException) {
        }
    }

    if (applicationInfo.value != null) {
        val info = applicationInfo.value!!
        val icon = info.loadIcon(packageManager)
        val appName = packageManager.getApplicationLabel(info).toString()

        onAppInfoLoaded(appName, icon.toBitmap().asImageBitmap())
    } else {
        onAppInfoLoaded("noapp", ImageBitmap(1, 1))
    }
}
