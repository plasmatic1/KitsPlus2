package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.commands.messages.ErrorMessages
import me.tlwv2.kitsp2.commands.messages.InfoMessages
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.Material
import org.bukkit.entity.Player

class EditKitCommand(main: Main) : KitsPlusCommand(Perms.EDIT_KIT, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        if(args.size != 2){
            ErrorMessages.argLength(player, args.size, 2)
            return false
        }

        val kit = main.searchKit(args[0])
        var failed = false

        if(kit == null){
            ErrorMessages.kitDoesNotExist(player, args[0])
            return true
        }

        /*
        Possible choices

        icon -> first validates the icon, then it replaces the current one and modifies the icon map
        inventory -> sets the inventory contents
        potion effects -> same as inventory but with effects
         */
        when(args[1]){
            "icon" -> {
                val item = player.inventory.itemInMainHand.clone()

                if(item.type == Material.ENCHANTED_BOOK || item.type == Material.AIR){
                    ErrorMessages.invalid(player, "icon (Item cannot be an enchanted book or air)!")
                    return true
                }

                kit.icon = item

                main.kitIconMap.remove(kit.icon)
                main.kitIconMap[kit.icon] = kit
            }
            "inventory" -> {
                kit.inventory = player.inventory.contents.clone()
            }
            "effects" -> {
                kit.effects = player.activePotionEffects.toTypedArray()
            }
            else -> {
                failed = true
                ErrorMessages.invalidChoice(player, args[1])
            }
        }

        if(!failed)
            InfoMessages.success(player)

        return true
    }
}