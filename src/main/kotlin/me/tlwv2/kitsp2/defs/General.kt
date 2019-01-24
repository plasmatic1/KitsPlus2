package me.tlwv2.kitsp2.defs

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
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

        // -- Important ItemStacks used by the GUI Inventory --
        val BORDER = namedItem("${Chat.BLACK}-", Material.BLACK_STAINED_GLASS_PANE)
        val INVALID = namedItem("${Chat.BLACK}-", Material.GRAY_STAINED_GLASS_PANE)
        val BACK = namedItem("${Chat.RED}Back", Material.RED_STAINED_GLASS_PANE)
        val ADD_KIT = namedItem("${Chat.AQUA}Add Folder/Kit", Material.NETHER_STAR)
        val DELETE_FOLDER = namedItem("${Chat.RED}Delete Folder", Material.REDSTONE_BLOCK)

        const val INV_SIZE = 54

        // -- GUI Inventory Templates and Constants --
        val INVALID_INVENTORY: Inventory =
            Bukkit.createInventory(null, 9, "Invalid!")
        var VALID_SLOTS: MutableList<Int> = mutableListOf()
        private var BASE_TEMPLATE: Array<ItemStack?> = arrayOfNulls(INV_SIZE)

        init {
            for (i in 0..8)
                BASE_TEMPLATE[i] = BORDER

            for (i in 45..53)
                BASE_TEMPLATE[i] = BORDER

            for (i in 0..45 step 9) {
                BASE_TEMPLATE[i] = BORDER
                BASE_TEMPLATE[i + 8] = BORDER
            }

            for (i in 10..37 step 9) {
                for (j in i..i + 6) {
                    BASE_TEMPLATE[i] = INVALID
                    VALID_SLOTS.add(j)
                }
            }
        }

        // -- The actual templates used by the other classes --
        var EDIT_TEMPLATE: Array<ItemStack?> = BASE_TEMPLATE.clone()
        var DEFAULT_TEMPLATE: Array<ItemStack?> = BASE_TEMPLATE.clone()

        init{
            EDIT_TEMPLATE[47] = ADD_KIT
            EDIT_TEMPLATE[49] = BACK
            EDIT_TEMPLATE[51] = DELETE_FOLDER

            DEFAULT_TEMPLATE[49] = BACK
        }
    }
}