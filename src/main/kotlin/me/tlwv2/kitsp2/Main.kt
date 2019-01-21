package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.commands.CommandData
import me.tlwv2.kitsp2.commands.HelpCommand
import me.tlwv2.kitsp2.commands.KitsPlusCommand
import org.bukkit.plugin.java.JavaPlugin

typealias Serialized = MutableMap<String, Object?>

class Main : JavaPlugin() {
    var commands: MutableList<CommandData> = mutableListOf()

    override fun onEnable() {
        super.onEnable()

        // -- Registering Commands

        //...
        registerCommand("help", HelpCommand(this))

        // -- Registering Listener
        EventListener(this)
    }

    override fun onDisable() {
        super.onDisable()
    }

    fun registerCommand(name: String, command: KitsPlusCommand){
        getCommand(name).executor = command
        commands.add(CommandData(name, command.permission))
    }
}
