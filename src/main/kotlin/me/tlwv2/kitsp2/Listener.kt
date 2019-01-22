package me.tlwv2.kitsp2

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class EventListener(val main: Main) : Listener{
    init{
        Bukkit.getPluginManager().registerEvents(this, this.main)
    }

    @EventHandler fun onInventoryClick(event: InventoryClickEvent){
        TODO("Implement the inventory click interaction with GUI menus")
    }

    @EventHandler fun onInventoryClose(event: InventoryCloseEvent){
        TODO("Implement the halting of tracking state when a player closes an inventory")
    }
}