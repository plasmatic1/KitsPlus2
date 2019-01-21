package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.commands.CommandData
import me.tlwv2.kitsp2.commands.KitsPlusCommand
import me.tlwv2.kitsp2.commands.PermissionInfoCommand
import me.tlwv2.kitsp2.defs.GeneralKeys
import me.tlwv2.kitsp2.defs.KitMap
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    var commands: MutableList<CommandData> = mutableListOf()

    lateinit var rootFolder: Folder
    lateinit var unusedKits: KitMap

    override fun onLoad() {
        super.onLoad()

        ConfigurationSerialization.registerClass(Kit::class.java)
        ConfigurationSerialization.registerClass(Folder::class.java)
    }

    override fun onEnable() {
        super.onEnable()

        // -- Getting Configuration Data
        rootFolder = config.get(GeneralKeys.ROOT_FOLDER, Folder()) as Folder
        unusedKits = config.get(GeneralKeys.UNUSED_KITS,
            mutableMapOf<String, Kit>()) as KitMap

        // -- Registering Commands

        //...
        registerCommand("help", PermissionInfoCommand(this))

        // -- Registering Listener
        EventListener(this)
    }

    override fun onDisable() {
        super.onDisable()

        config.set(GeneralKeys.ROOT_FOLDER, rootFolder)
        config.set(GeneralKeys.UNUSED_KITS, unusedKits)
    }

    fun registerCommand(name: String, command: KitsPlusCommand){
        getCommand(name).executor = command
        commands.add(CommandData(name, command.permission))
    }
}
