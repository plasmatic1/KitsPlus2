package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class KitTreeCommand(main: Main) : KitsPlusCommand(Perms.KIT_TREE, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        TODO("Not Implemented")
        return true
    }
}