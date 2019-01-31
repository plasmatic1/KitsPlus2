package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Folder
import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.defs.Chat
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class KitTreeCommand(main: Main) : KitsPlusCommand(Perms.KIT_TREE, main) {
    /*
    Multiplies a string <string> by <times> times.  This is identical to the behaviour of the
    multiplication operator with strings in python
     */
    private fun multiplyString(string: String, times: Int): String{
        var finalString = ""
        var counter: Int = times

        while(counter > 0){
            finalString += string
            counter--
        }

        return finalString
    }

    /*
    Recursively prints the current kit tree to the player specified
     */
    private fun printKitTree(player: Player, currFolder: Folder, currName: String, tabCount: Int){
        val base = "${multiplyString("  ", tabCount)}${Chat.GOLD}- ${Chat.GREY}"
        player.sendMessage("$base$currName")

        for(entry in currFolder.subfolders)
            printKitTree(player, entry.value, entry.key, tabCount + 1)

        for(entry in currFolder.kits)
            player.sendMessage("  ${base}Kit: ${entry.key}")
    }

    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        printKitTree(player, main.rootFolder, "root", 0)

        return true
    }
}