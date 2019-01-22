package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.defs.Chat
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class KitsPlusCommand(val permission: String?, protected val main: Main): CommandExecutor{
    override fun onCommand(sender: CommandSender?, a: Command, b: String, args: Array<out String>?): Boolean {
        if(sender !is Player){
            sender!!.sendMessage(Chat.NOT_PLAYER_MESSAGE)
            return true
        }

        if(this.permission != null && !sender.hasPermission(permission)){
            sender!!.sendMessage(Chat.NO_PERM_MESSAGE)
            return true
        }

        return this.onPlayerCommand(sender, args!!)
    }

    abstract fun onPlayerCommand(player: Player, args: Array<out String>): Boolean
}

data class CommandData(val name: String, val permission: String?)