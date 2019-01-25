package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.defs.GUIItems
import org.bukkit.Bukkit
import org.bukkit.ChatColor
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

        // As long as the player is currently in a kit context this will process the inventory click event
        if (uuid in main.contexts) {
            // If the type is an enchanted book, process the click as if it was going into a subfolder
            if(currentItem.type == Material.ENCHANTED_BOOK){
                val folderName = ChatColor.stripColor(currentItem.itemMeta.displayName)
                val subfolder = main.contexts.remove(uuid)!!.subfolders[folderName]!!

                main.contexts[uuid] = subfolder

                main.skipClose.add(uuid)
                player.openInventory(subfolder.inventory)
            }
            // If the type is similar to the "back" button, process the click as if it was the back button
            else if(currentItem.isSimilar(GUIItems.BACK)){
                var folder = main.contexts[uuid]!!

                if(folder.parent != null){
                    main.contexts.remove(uuid)
                    main.contexts[uuid] = folder.parent!!

                    main.skipClose.add(uuid)
                    player.openInventory(folder.parent!!.inventory)
                }
            }
            // As long as it isn't one of the invalid or border item stacks, process it as if it was a kit icon
            else if(!(currentItem.isSimilar(GUIItems.INVALID) || currentItem.isSimilar(GUIItems.BORDER))){
                val kit = main.kitIconMap[currentItem]!!

                kit.apply(player)
                main.contexts.remove(uuid)
                player.closeInventory()
            }

            event.isCancelled = true
        }
    }

    /*
    If a player closes an inventory and they were in a context, then assume that they are leaving the context.
    This is to prevent people from being stuck within a context if they don't choose a kit.
     */
    @EventHandler fun onInventoryClose(event: InventoryCloseEvent){
        val uuid = event.player.uniqueId

        if(uuid in main.skipClose){
            main.skipClose.remove(uuid)
            return
        }

        if(uuid in main.contexts)
            main.contexts.remove(uuid)
    }
}