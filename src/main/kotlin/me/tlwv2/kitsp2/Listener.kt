package me.tlwv2.kitsp2

import org.bukkit.Bukkit
import org.bukkit.event.Listener

class EventListener(val main: Main) : Listener{
    init{
        Bukkit.getPluginManager().registerEvents(this, this.main)
    }
}