package kr.blugon.pluginhelper.etc

import kr.blugon.pluginhelper.component.component
import kr.blugon.pluginhelper.etc.Title.sendTitle
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.TitlePart
import org.bukkit.entity.Player
import java.time.Duration

object Title {

    //시간 단위 : tick
    //unit of time : tick

    fun Player.sendTitle(title : Component, subtitle : Component, fadeIn : Long, stay : Long, fadeOut : Long) {
        sendTitlePart(
            TitlePart.TIMES, net.kyori.adventure.title.Title.Times.times(
                Duration.ofMillis((fadeIn/20.0*1000).toLong()),
                Duration.ofMillis((stay/20.0*1000).toLong()),
                Duration.ofMillis((fadeOut/20.0*1000).toLong())
            )
        )
        sendTitlePart(TitlePart.SUBTITLE, subtitle)
        sendTitlePart(TitlePart.TITLE, title)
    }

    fun Player.sendTitle(title : Component, fadeIn : Long, stay : Long, fadeOut : Long) {
        sendTitlePart(
            TitlePart.TIMES, net.kyori.adventure.title.Title.Times.times(
                Duration.ofMillis((fadeIn/20.0*1000).toLong()),
                Duration.ofMillis((stay/20.0*1000).toLong()),
                Duration.ofMillis((fadeOut/20.0*1000).toLong())
            )
        )
        sendTitlePart(TitlePart.SUBTITLE, " ".component())
        sendTitlePart(TitlePart.TITLE, title)
    }

    fun Player.sendTitle(title : Component, subtitle : Component) {
        sendTitlePart(TitlePart.SUBTITLE, subtitle)
        sendTitlePart(TitlePart.TITLE, title)
    }

    fun Player.sendTitle(title : Component) {
        sendTitlePart(TitlePart.TITLE, title)
    }

    fun Player.setTitleTime(fadeIn : Long, stay : Long, fadeOut : Long) {
        sendTitlePart(
            TitlePart.TIMES, net.kyori.adventure.title.Title.Times.times(
                Duration.ofMillis((fadeIn/20.0*1000).toLong()),
                Duration.ofMillis((stay/20.0*1000).toLong()),
                Duration.ofMillis((fadeOut/20.0*1000).toLong())
            )
        )
    }
}