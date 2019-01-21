package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.defs.FolderKeys
import me.tlwv2.kitsp2.defs.FolderMap
import me.tlwv2.kitsp2.defs.KitMap
import me.tlwv2.kitsp2.defs.Serialized
import org.bukkit.configuration.serialization.ConfigurationSerializable

class Folder() : ConfigurationSerializable{
    var subfolders: FolderMap = mutableMapOf()
    var kits: KitMap = mutableMapOf()

    var size: Int = -1
    get() = this.subfolders.size + this.kits.size

    fun searchKit(name: String): Boolean{
        if(this.kits.containsKey(name)) return true

        for(entry in this.subfolders)
            if(entry.value.searchKit(name)) return true

        return false
    }

    fun removeKit(name: String): Boolean{
        if(this.kits.remove(name) != null) return true

        for(entry in this.subfolders)
            if(entry.value.removeKit(name)) return true

        return false
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