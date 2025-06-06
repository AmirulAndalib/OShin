package com.suqi8.oshin.hook.com.android.systemui.StatusBar

import android.view.View
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass

class Fragment: YukiBaseHooker() {
    override fun onHook() {
        if (prefs("systemui").getBoolean("hide_status_bar", false)) {
            loadApp(name = "com.android.systemui") {
                "com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment".toClass().apply {
                    method {
                        name = "onViewCreated"
                        param(ViewClass, BundleClass)
                    }.hook {
                        after {
                            val view = args[0] as View
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }
}
