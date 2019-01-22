package me.tlwv2.kitsp2.defs

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Perms{
    companion object{
        // -- Basic Kit Manipulation --
        const val CREATE_KIT = "kitsp.use.createkit"
        const val DELETE_KIT = "kitsp.use.deletekit"
        const val EDIT_KIT = "kitsp.use.editkit"

        // -- Kit GUI --
        const val KIT_GUI = "kitsp.use.kitgui"
        const val EDIT_KIT_GUI = "kitsp.use.editkitgui"

        // -- Listing Kit Info --
        const val PERMISSION_INFO = "kitsp.use.pinfo"
        const val KIT_TREE = "kitsp.use.kittree"
        const val LIST_UNUSED = "kitsp.use.listunused"
    }
}

class Chat{
    companion object {
        const val BLACK = "§0"
        const val RED = "§c"
        const val GOLD = "§6"
        const val GREY = "§7"
        const val AQUA = "§b"
        const val GREEN = "§a"

        const val NO_PERM_MESSAGE = "${RED}Insufficient Permissions!"
        const val NOT_PLAYER_MESSAGE = "${RED}Only a Player can execute this command!"
    }
}

class GUIItems{
    companion object {
        fun namedItem(name: String, material: Material): ItemStack{
            var stack = ItemStack(material)
            var meta = stack.itemMeta

            meta.displayName = name
            stack.itemMeta = meta

            return stack
        }

        val BORDER = namedItem("${Chat.BLACK}-", Material.BLACK_STAINED_GLASS_PANE)
        val INVALID = namedItem("${Chat.BLACK}-", Material.GRAY_STAINED_GLASS_PANE)
        val BACK = namedItem("${Chat.RED}Back", Material.RED_STAINED_GLASS_PANE)
        val ADD_KIT = namedItem("${Chat.AQUA}Add Folder/Kit", Material.NETHER_STAR)
        val DELETE_FOLDER = namedItem("${Chat.RED}Delete Folder", Material.REDSTONE_BLOCK)
    }
}