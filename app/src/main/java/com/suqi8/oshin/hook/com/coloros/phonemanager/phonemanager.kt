package com.suqi8.oshin.hook.com.coloros.phonemanager

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.UnitType

class phonemanager: YukiBaseHooker() {
    override fun onHook() {
        var hasani = 0
        loadApp(name = "com.coloros.phonemanager") {
            if (prefs("phonemanager").getBoolean("remove_all_popup_delays", false)) {
                "com.oplus.phonemanager.common.DialogCrossActivity\$f".toClass().apply {
                    method {
                        name = "onTick"
                        returnType = UnitType
                    }.hook {
                        before {
                            args[0] = 0
                        }
                    }
                }
            }
            "com.oplus.phonemanager.common.view.ScanCircleView".toClass().apply {
                method {
                    name = "B"
                    param(IntType, BooleanType, "java.lang.Long", BooleanType)
                    returnType = UnitType
                }.hook {
                    before {
                        if (prefs("phonemanager").getInt("custom_score", -1) != -1) {
                            if (args[1] as Boolean) {
                                hasani += 1
                                if (hasani == 2) {
                                    args[0] = prefs("phonemanager").getInt("custom_score", -1)
                                }
                            }
                        }
                        if (prefs("phonemanager").getInt("custom_animation_duration", -1) != -1) {
                            args[2] = prefs("phonemanager").getInt("custom_animation_duration", -1).toLong()
                        }
                    }
                }
            }
            if (prefs("phonemanager").getString("custom_prompt_content", "") != "") {
                "com.oplus.phonemanager.newrequest.delegate.m0".toClass().apply {
                    method {
                        name = "a"
                        param("java.util.List", IntType)
                        returnType = "java.lang.String"
                    }.hook {
                        before {
                            result = prefs("phonemanager").getString("custom_prompt_content", "")
                        }
                    }
                }
            }
        }
    }
}
