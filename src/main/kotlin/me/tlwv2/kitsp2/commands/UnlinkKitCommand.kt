package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class UnlinkKitCommand(main: Main) : KitsPlusCommand(Perms.UNLINK_KIT, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        TODO("Command")

        return true
    }
}