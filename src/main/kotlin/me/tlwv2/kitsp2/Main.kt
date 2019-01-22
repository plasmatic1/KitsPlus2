package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.commands.*
import me.tlwv2.kitsp2.defs.GeneralKeys
import me.tlwv2.kitsp2.defs.KitMap
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    var commands: MutableList<CommandData> = mutableListOf()

    lateinit var rootFolder: Folder
    lateinit var unusedKits: KitMap
    lateinit var kitIconMap: MutableMap<ItemStack, Kit>

    //TODO: Maintain state for players that are using the Kit Gui, whether to find, edit, or do whatever

    override fun onLoad() {
        super.onLoad()

        ConfigurationSerialization.registerClass(Kit::class.java)
        ConfigurationSerialization.registerClass(Folder::class.java)
    }

    override fun onEnable() {
        super.onEnable()

        // -- Getting Configuration Data and Initializing Things
        rootFolder = config.get(GeneralKeys.ROOT_FOLDER, Folder()) as Folder
        unusedKits = config.get(GeneralKeys.UNUSED_KITS,
            mutableMapOf<String, Kit>()) as KitMap

        rootFolder.traverseKits { name, kit -> kitIconMap.put(kit.icon, kit) }

        // -- Registering Commands
        registerCommand("createkit", CreateKitCommand(this))
        registerCommand("deletekit", DeleteKitCommand(this))
        registerCommand("editkit", EditKitCommand(this))

        registerCommand("kitgui", KitGuiCommand(this))
        registerCommand("editkitgui", EditKitGuiCommand(this))

        registerCommand("kittree", KitTreeCommand(this))
        registerCommand("listunused", ListUnusedCommand(this))
        registerCommand("premissioninfo", PermissionInfoCommand(this))

        // -- Misc. Initialization
        EventListener(this)
    }

    override fun onDisable() {
        super.onDisable()

        config.set(GeneralKeys.ROOT_FOLDER, rootFolder)
        config.set(GeneralKeys.UNUSED_KITS, unusedKits)
    }

    // -------------------------
    // General Utility Functions
    // -------------------------

    fun registerCommand(name: String, command: KitsPlusCommand){
        getCommand(name).executor = command
        commands.add(CommandData(name, command.permission))
    }

    // --------------------------------------
    // Utility Functions for Kit Manipulation
    // --------------------------------------

    // Searching and Checks

    fun searchKit(name: String) : Kit? = rootFolder.searchKit(name) ?: unusedKits[name]

    fun removeKit(name: String){
        if(!rootFolder.removeKit(name))
            unusedKits.remove(name)
    }

    fun iconExists(icon: ItemStack) : Boolean = kitIconMap.containsKey(icon)
}
