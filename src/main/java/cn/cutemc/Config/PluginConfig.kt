package cn.cutemc.Config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object PluginConfig : AutoSavePluginConfig("PluginConfig"){

    val enableBot : List<Long> by value()
    val enableGroup : List<Long> by value()

}