package kr.blugon.pluginhelper.etc

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object Scheduler {
    fun schedulerDelayTask(plugin : JavaPlugin, delay : Long, task : ()->Unit) : Int {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task, delay)
    }

    fun schedulerRepeatingTask(plugin: JavaPlugin, delay : Long, period: Long, task: () -> Unit) : Int {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, delay, period)
    }
}