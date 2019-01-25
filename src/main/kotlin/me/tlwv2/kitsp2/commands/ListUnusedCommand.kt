package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.defs.Chat
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class ListUnusedCommand(main: Main) : KitsPlusCommand(Perms.LIST_UNUSED, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        player.sendMessage("${Chat.GOLD}-- ${Chat.GREY}Currently Unused Kits ${Chat.GOLD}--")
        for(kitName in main.unusedKits.keys)
            player.sendMessage("${Chat.GOLD}- ${Chat.GREY}$kitName")

        return true
    }
}