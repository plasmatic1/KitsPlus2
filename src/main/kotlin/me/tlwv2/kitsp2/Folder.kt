package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.defs.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.DoubleChestInventory

class Folder() : ConfigurationSerializable{
    var subfolders: FolderMap = mutableMapOf()
    var kits: KitMap = mutableMapOf()
    var parent: Folder? = null

    var size: Int = -1
    get() = this.subfolders.size + this.kits.size

    /*
    Recursively assigns parent folders to the current operating tree
     */
    fun assignParents(currParent: Folder? = null){
        parent = currParent

        for(subfolder in this.subfolders.values)
            subfolder.assignParents(this)
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
    This applies the consumer to every single kit
     */
    fun traverseKits(consumer: (String, Kit) -> Unit){
        for(entry in this.kits)
            consumer(entry.key, entry.value)

        for(entry in this.subfolders)
            entry.value.traverseKits(consumer)
    }

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

enum class ContextType{
    DEFAULT, EDIT
}

/*
The inventory getter (a.k.a. the getInventory() function) builds an inventory for the
folder in this context.

Note that the getter assumes that the total size of the folder is less than or equal to 28
*/
class FolderContext(val folder: Folder, val type: ContextType){
    var inventory: DoubleChestInventory = GUIItems.INVALID_INVENTORY as DoubleChestInventory
    get() {
        // Initializing variables
        var contents = when(type){
            ContextType.DEFAULT -> GUIItems.DEFAULT_TEMPLATE.clone()
            ContextType.EDIT -> GUIItems.EDIT_TEMPLATE.clone()
        }
        val inventory = Bukkit.createInventory(null, GUIItems.INV_SIZE) as DoubleChestInventory
        val slotIter = GUIItems.VALID_SLOTS.iterator()

        // Setting contents to folders and kits
        for(entry in folder.subfolders)
            contents[slotIter.next()] = GUIItems.namedItem(entry.key, Material.ENCHANTED_BOOK)

        for(kit in folder.kits.values)
            contents[slotIter.next()] = kit.icon.clone()

        inventory.contents = contents

        return inventory
    }
}