package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.defs.FolderKeys
import me.tlwv2.kitsp2.defs.FolderMap
import me.tlwv2.kitsp2.defs.KitMap
import me.tlwv2.kitsp2.defs.Serialized
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.DoubleChestInventory

class Folder() : ConfigurationSerializable{
    var subfolders: FolderMap = mutableMapOf()
    var kits: KitMap = mutableMapOf()

    var size: Int = -1
    get() = this.subfolders.size + this.kits.size

    fun searchKit(name: String): Kit?{
        var ret = this.kits[name]
        if(ret != null) return ret

        for(entry in this.subfolders) {
            ret = entry.value.searchKit(name)
            if(ret != null) return ret
        }

        return null
    }

    fun removeKit(name: String): Boolean{
        if(this.kits.remove(name) != null) return true

        for(entry in this.subfolders) {
            if(entry.value.removeKit(name)) return true
        }

        return false
    }

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

enum class FolderContext{
    ROOT, ROOT_EDIT, DEFAULT, DEFAULT_EDIT, CHOOSE_KIT
}

class FolderContextManager{
    var inventory: DoubleChestInventory? = null

}