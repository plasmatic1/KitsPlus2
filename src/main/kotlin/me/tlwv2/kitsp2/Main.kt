package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.commands.*
import me.tlwv2.kitsp2.defs.ContextMap
import me.tlwv2.kitsp2.defs.GeneralKeys
import me.tlwv2.kitsp2.defs.KitMap
import org.bukkit.Bukkit
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Main : JavaPlugin() {
    var commands: MutableList<CommandData> = mutableListOf()

    lateinit var rootFolder: Folder
    var unusedKits: KitMap = mutableMapOf()
    var kitIconMap: MutableMap<ItemStack, Kit> = mutableMapOf()

    /*
    A map of all "folder contexts" that are currently open.
    A folder context is just a player that has a folder open

    skipClose is a Mutable Set of all the players that should be skipped for
    their next InventoryCloseEvent.  This is to prevent the erroneous removal of
    folder contexts when players are moving into sub/parent folders.
     */
    var contexts: ContextMap = mutableMapOf()
    var skipClose: MutableSet<UUID> = mutableSetOf()

    override fun onLoad() {
        super.onLoad()

        ConfigurationSerialization.registerClass(Kit::class.java)
        ConfigurationSerialization.registerClass(Folder::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onEnable() {
        super.onEnable()

        // -- Getting Configuration Data and Initializing Things
        rootFolder = config.get(GeneralKeys.ROOT_FOLDER, Folder()) as Folder

        if(config.contains(GeneralKeys.UNUSED_KITS)){
            val section = config.getConfigurationSection(GeneralKeys.UNUSED_KITS)
            for(key in section.getKeys(false))
                unusedKits[key] = section[key] as Kit
        }

        rootFolder.traverseKits { _, kit -> kitIconMap[kit.icon] = kit }
        rootFolder.assignParents()

        logger.info("Parsed configuration data!")

        // -- Registering Commands
        registerCommand("createkit", CreateKitCommand(this))
        registerCommand("deletekit", DeleteKitCommand(this))
        registerCommand("editkit", EditKitCommand(this))

        registerCommand("linkkit", LinkKitCommand(this))
        registerCommand("unlinkkit", UnlinkKitCommand(this))
        registerCommand("unlinkfolder", UnlinkFolderCommand(this))

        registerCommand("kitgui", KitGuiCommand(this))

        registerCommand("kittree", KitTreeCommand(this))
        registerCommand("listunused", ListUnusedCommand(this))
        registerCommand("permissioninfo", PermissionInfoCommand(this))

        logger.info("Registered commands!")

        // -- Misc. Initialization
        EventListener(this)
        Folder.main = this

        logger.info("Finished initialization!")
    }

    override fun onDisable() {
        super.onDisable()

        config.set(GeneralKeys.ROOT_FOLDER, rootFolder)
        config.set(GeneralKeys.UNUSED_KITS, unusedKits)
        this.saveConfig()
    }

    // -------------------------
    // General Utility Functions
    // -------------------------

    /*
    Registers a command
     */
    fun registerCommand(name: String, command: KitsPlusCommand){
        Bukkit.getPluginCommand(name)!!.executor = command
        commands.add(CommandData(name, command.permission))

        logger.info("Registered command /$name")
    }

    // --------------------------------------
    // Utility Functions for Kit Manipulation
    // --------------------------------------

    /*
    Searches for a kit with the given name from both the root tree and the unused kits map
     */
    fun searchKit(name: String) : Kit? = rootFolder.searchKit(name) ?: unusedKits[name]

    /*
    Removes a kit with the given name from both the root tree and the unused kits map
     */
    fun removeKit(name: String){
        if(!rootFolder.removeKit(name))
            unusedKits.remove(name)
    }

    /*
    Function for whether the icon has already been registered under a kit
     */
    fun iconExists(icon: ItemStack) : Boolean = kitIconMap.containsKey(icon)
}
