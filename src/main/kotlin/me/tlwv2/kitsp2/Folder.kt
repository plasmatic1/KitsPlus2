package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.defs.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.Inventory

@Suppress("UNCHECKED_CAST")
class Folder() : ConfigurationSerializable{
    companion object {
        lateinit var main: Main
    }

    var subfolders: FolderMap = mutableMapOf()
    var kits: KitMap = mutableMapOf()
    var parent: Folder? = null
    lateinit var name: String

    var size: Int = -1
    get() = this.subfolders.size + this.kits.size

    /*
    The inventory getter (a.k.a. the getInventory() function) builds an inventory for the
    folder in this context.

    Note that the getter assumes that the total size of the folder is less than or equal to 28
    */
    var inventory: Inventory = GUIItems.INVALID_INVENTORY
    get() {
        // Initializing variables
        var contents = GUIItems.DEFAULT_TEMPLATE.clone()
        val inventory = Bukkit.createInventory(null, GUIItems.INV_SIZE, this.name)
        val slotIter = GUIItems.VALID_SLOTS.iterator()

        // Setting contents to folders and kits
        for(entry in this.subfolders)
            contents[slotIter.next()] = GUIItems.namedItem("${Chat.AQUA}${entry.key}", Material.ENCHANTED_BOOK)

        for(kit in this.kits.values)
            contents[slotIter.next()] = kit.icon.clone()

        inventory.contents = contents

        return inventory
    }

    /*
    Recursively assigns parent folders to the current operating tree
     */
    fun assignParents(currParent: Folder? = null, name: String = "Kits"){
        parent = currParent
        this.name = name

        for(entry in this.subfolders)
            entry.value.assignParents(this, entry.key)
    }

    /*
    Searches for the kit with the given name.  If the kit is found, the object is returned.
    Otherwise, the function returns null.
     */
    fun searchKit(name: String): Kit?{
        var ret = this.kits[name]
        if(ret != null) return ret

        for(entry in this.subfolders) {
            ret = entry.value.searchKit(name)
            if(ret != null) return ret
        }

        return null
    }

    /*
    Removes the kit with the given name.  If the removal was successful, the function returns true.
    Otherwise, the function will return false.

    In this case, the only reason the function will fail is that the kit wasn't found
     */
    fun removeKit(name: String): Boolean{
        if(this.kits.remove(name) != null) return true

        for(entry in this.subfolders)
            if(entry.value.removeKit(name)) return true

        return false
    }

    /*
    Function that removes all subfolders and moves all kits into the unused section.  This is called by the
    Folder.removeFolder(name) function
     */
    fun deconstruct(): Boolean{
        for(entry in this.kits)
            main.unusedKits[entry.key] = entry.value

        for(entry in this.subfolders)
            entry.value.deconstruct()

        this.kits.clear()
        this.subfolders.clear()

        return true
    }

    /*
    This applies the consumer to every single kit
     */
    fun traverseKits(consumer: (String, Kit) -> Unit){
        for(entry in this.kits)
            consumer(entry.key, entry.value)

        for(entry in this.subfolders)
            entry.value.traverseKits(consumer)
    }

    /*
    Serialization functions
     */
    constructor(map: Serialized) : this(){
        this.subfolders = map[FolderKeys.SUBFOLDERS] as FolderMap
        this.kits = map[FolderKeys.KITS] as KitMap
    }

    override fun serialize(): Serialized {
        var map: Serialized = mutableMapOf()

        map[FolderKeys.SUBFOLDERS] = this.subfolders
        map[FolderKeys.KITS] = this.kits

        return map
    }
}