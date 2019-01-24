package me.tlwv2.kitsp2

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class EventListener(val main: Main) : Listener{
    init{
        Bukkit.getPluginManager().registerEvents(this, this.main)
    }

    @EventHandler fun onInventoryClick(event: InventoryClickEvent){
        val player = event.whoClicked as Player
        val uuid = player.uniqueId
        val currentItem = event.currentItem

        event.isCancelled = true

        when (uuid) {
            in main.mainContexts -> {
                // Process as folder
                if(currentItem.type == Material.ENCHANTED_BOOK){
                    val folderName = currentItem.itemMeta.displayName
                    val context = main.mainContexts.remove(uuid)!!
                    val newContext =
                        FolderContext(.
                            folder.subfolders[folderName]!!, ContextType.DEFAULT)

                }
                // Process as kit
                else{
                    TODO("kit processing")
                }
            }
            in main.pickerContexts -> {
                //
            }
            else -> event.isCancelled = false
        }

        TODO("Implement the inventory click interaction with GUI menus")
    }

    @EventHandler fun onInventoryClose(event: InventoryCloseEvent){
        TODO("Implement the halting of tracking state when a player closes an inventory")
    }
}