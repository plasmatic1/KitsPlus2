package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class EditKitGuiCommand(main: Main) : KitsPlusCommand(Perms.EDIT_KIT_GUI, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        TODO("Not Implemented")
        return true
    }
}