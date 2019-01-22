package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.commands.messages.ErrorMessages
import me.tlwv2.kitsp2.commands.messages.InfoMessages
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class EditKitCommand(main: Main) : KitsPlusCommand(Perms.EDIT_KIT, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        if(args.size != 2){
            ErrorMessages.argLength(player, args.size, 2)
            return true
        }

        val kit = main.searchKit(args[0])
        var failed = false

        if(kit == null){
            ErrorMessages.kitDoesNotExist(player, args[0])
            return true
        }

        when(args[1]){
            "icon" -> {
                kit!!.icon = player.inventory.itemInMainHand.clone()
                main.kitIconMap.remove(kit!!.icon)
                main.kitIconMap[kit!!.icon] = kit!!
            }
            "inventory" -> {
                kit!!.inventory = player.inventory.contents.clone()
            }
            "effects" -> {
                kit!!.effects = player.activePotionEffects.toTypedArray()
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