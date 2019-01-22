package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.defs.Chat
import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class PermissionInfoCommand(main: Main) : KitsPlusCommand(Perms.PERMISSION_INFO, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        player.sendMessage("${Chat.GOLD}-- ${Chat.GREY}Permissions Info ${Chat.GOLD}--")
        for(command: CommandData in main.commands){
            player.sendMessage("${Chat.GOLD}- " +
                    "${Chat.GREY}/${command.name} ${Chat.GOLD}: " +
                    "${Chat.GREY}${command.permission ?: "None"}")
        }

        return true
    }
}
