package me.tlwv2.kitsp2.defs

import me.tlwv2.kitsp2.Folder
import me.tlwv2.kitsp2.Kit
import java.util.*

typealias Serialized = MutableMap<String, Any?>
typealias KitMap = MutableMap<String, Kit>
typealias FolderMap = MutableMap<String, Folder>
typealias ContextMap = MutableMap<UUID, Folder>

class KitKeys{
    companion object {
        const val ICON = "icon"
        const val INVENTORY = "inventory"
        const val EFFECTS = "effects"
    }
}

class FolderKeys{
    companion object {
        const val SUBFOLDERS = "subfolders"
        const val KITS = "kits"
    }
}

class GeneralKeys{
    companion object {
        const val ROOT_FOLDER = "rootFolder"
        const val UNUSED_KITS = "unusedKits"
    }
}

class FolderValues{
    companion object {
        const val MAX_SIZE = 28
    }
}