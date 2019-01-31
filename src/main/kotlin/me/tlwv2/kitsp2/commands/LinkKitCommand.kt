package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Folder
import me.tlwv2.kitsp2.Kit
import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.commands.messages.ErrorMessages
import me.tlwv2.kitsp2.commands.messages.InfoMessages
import me.tlwv2.kitsp2.defs.Chat
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class LinkKitCommand(main: Main) : KitsPlusCommand(Perms.LINK_KIT, main) {
    /*
    This is a function that recursively travels down the folder tree until it hits the path of the kit.  If such a
    path does not exist, it will be created whether you like it or not.
     */
    private fun linkKit(currFolder: Folder, currIndex: Int, path: List<String>, kitName: String, kit: Kit){
        if(currIndex == path.size) {
            currFolder.kits[kitName] = kit

            if(!main.kitIconMap.containsKey(kit.icon))
                main.kitIconMap[kit.icon] = kit
        }
        else{
            var nextFolder = currFolder.subfolders[path[currIndex]]

            if(nextFolder == null){
                nextFolder = Folder()
                nextFolder.parent = currFolder
                nextFolder.name = path[currIndex]
                currFolder.subfolders[path[currIndex]] = nextFolder
            }

            linkKit(nextFolder, currIndex + 1, path, kitName, kit)
        }
    }

    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        if(args.size != 2){
            ErrorMessages.argLength(player, args.size, 2)
            return false
        }

        var kit = main.unusedKits[args[1]]

        if(kit == null){
            kit = main.rootFolder.searchKit(args[1])
            player.sendMessage("${Chat.RED}Kit does not exist in unused kit section, attempting to fetch it from the root tree!")

            if(kit == null){
                ErrorMessages.kitDoesNotExist(player, args[1])
                return true
            }
            else
                main.rootFolder.removeKit(args[1])
        }
        else
            main.unusedKits.remove(args[1])

        if(args[0] == "root")
            main.rootFolder.kits[args[1]] = kit
        else
            linkKit(main.rootFolder, 0, args[0].split("."), args[1], kit)

        InfoMessages.success(player)

        return true
    }
}