package com.suqi8.oshin.ui.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.kyant.capsule.ContinuousRoundedRectangle
import com.suqi8.oshin.R
import com.suqi8.oshin.ui.activity.components.BasicComponent
import com.suqi8.oshin.ui.activity.components.BasicComponentColors
import com.suqi8.oshin.ui.activity.components.Card
import com.suqi8.oshin.ui.activity.components.CardDefaults
import com.suqi8.oshin.ui.activity.components.FunPage
import com.suqi8.oshin.ui.activity.components.addline
import com.suqi8.oshin.ui.activity.components.funArrow
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuix.kmp.utils.scrollEndHaptic

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun about_contributors(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val scrollBehavior = MiuixScrollBehavior(rememberTopAppBarState())

    FunPage(
        title = stringResource(id = R.string.contributors),
        navController = navController,
        scrollBehavior = scrollBehavior,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        animationKey = "about_contributors"
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .overScrollVertical()
                .scrollEndHaptic()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = padding
        ) {
            item {
                Card(colors = CardDefaults.defaultColors(color = MiuixTheme.colorScheme.primaryVariant.copy(alpha = 0.1f))) {
                    BasicComponent(
                        summary = stringResource(R.string.thanks_contributors),
                        summaryColor = BasicComponentColors(
                            color = MiuixTheme.colorScheme.primaryVariant,
                            disabledColor = MiuixTheme.colorScheme.primaryVariant
                        )
                    )
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 6.dp)
                ) {
                    item(
                        name = "YuKong_A",
                        github = "YuKongA"
                    )
                    addline()
                    item(
                        name = "天伞桜",
                        coolapk = "天伞桜",
                        coolapkid = 540690
                    )
                    addline()
                    item(
                        name = "shadow3",
                        github = "shadow3aaa"
                    )
                    addline()
                    item(
                        name = "凌逸",
                        coolapk = "网恋秀牛被骗",
                        coolapkid = 34081897
                    )
                    addline()
                    item(
                        name = "psychosispy",
                        github = "psychosispy"
                    )
                    addline()
                    item(
                        name = "咚踏取",
                        coolapk = "咚踏取",
                        coolapkid = 2035174
                    )
                    addline()
                    item(
                        name = "Mikusignal",
                        coolapk = "Mikusignal",
                        coolapkid = 12130388,
                        qq = 1809784522
                    )
                    addline()
                    item(
                        name = "hamjin",
                        github = "hamjin"
                    )
                    addline()
                    item(
                        name = "kmiit",
                        github = "kmiit"
                    )
                    addline()
                    item(
                        name = "fatal1101",
                        github = "fatal1101"
                    )
                }
            }
        }
    }
}

@Composable
internal fun item(
    name: String,
    coolapk: String? = null,
    coolapkid: Int? = null,
    github: String? = null,
    qq: Long? = null
) {
    val context = LocalContext.current
    var showExtra by remember { mutableStateOf(false) }
    val toastMessage = stringResource(R.string.please_install_cool_apk)

    // 公共启动函数
    fun launchUri(uri: Uri) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // 拼接summary字符串
    val summaryText = buildString {
        coolapk?.let { append("${stringResource(R.string.coolapk)}@$it ") }
        github?.let { append("Github@$it ") }
        qq?.let { append("QQ@$it ") }
    }
    funArrow(
        title = name,
        leftAction = {
            qq?.let {
                Column(modifier = Modifier
                    .padding(end = 10.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("https://q.qlogo.cn/headimg_dl?dst_uin=$it&spec=640&img_type=jpg")
                            .diskCachePolicy(CachePolicy.DISABLED) // 禁用磁盘缓存
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(ContinuousRoundedRectangle(15.dp))
                    )
                }
            }
        },
        summary = summaryText,
        onClick = {
            // 如果两个及以上信息存在，则弹出卡片，否则直接跳转
            val infoCount = listOfNotNull(coolapk, github, qq).size
            if (infoCount >= 2) {
                showExtra = !showExtra
            } else {
                when {
                    coolapk != null -> coolapkid?.let { launchUri("coolmarket://u/$it".toUri()) }
                    github != null -> launchUri("https://github.com/$github".toUri())
                    qq != null -> launchUri("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=$qq".toUri())
                }
            }
        }
    )

    AnimatedVisibility(visible = showExtra) {
        Card(
            colors = CardDefaults.defaultColors(color = MiuixTheme.colorScheme.secondaryContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Column {
                coolapk?.let {
                    funArrow(
                        title = stringResource(R.string.coolapk),
                        leftAction = {
                            Image(
                                painter = painterResource(R.drawable.coolapk),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 8.dp),
                                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface)
                            )
                        },
                        onClick = {
                            coolapkid?.let { id ->
                                launchUri("coolmarket://u/$id".toUri())
                            }
                        }
                    )
                    addline()
                }
                github?.let {
                    funArrow(
                        title = "Github",
                        leftAction = {
                            Image(
                                painter = painterResource(R.drawable.github),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 8.dp),
                                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface)
                            )
                        },
                        onClick = {
                            launchUri("https://github.com/$it".toUri())
                        }
                    )
                    addline()
                }
                qq?.let {
                    funArrow(
                        title = "QQ",
                        leftAction = {
                            Image(
                                painter = painterResource(R.drawable.qq),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 8.dp),
                                colorFilter = ColorFilter.tint(MiuixTheme.colorScheme.onSurface)
                            )
                        },
                        onClick = {
                            launchUri("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=$it".toUri())
                        }
                    )
                }
            }
        }
    }
}
