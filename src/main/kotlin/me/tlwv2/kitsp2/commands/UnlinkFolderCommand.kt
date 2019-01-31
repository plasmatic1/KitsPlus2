package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Folder
import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.commands.messages.ErrorMessages
import me.tlwv2.kitsp2.commands.messages.InfoMessages
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class UnlinkFolderCommand(main: Main) : KitsPlusCommand(Perms.UNLINK_FOLDER, main) {
    /*
    This is a function that recursively travels down the folder tree until it hits the path of the folder, then
    the folder is deconstructed.  If the folder is not found, the function returns false (otherwise it returns
    true)
     */
    private fun unlinkFolder(currFolder: Folder, currIndex: Int, path: List<String>): Boolean{
        if(currIndex == path.size - 1) {
            var folderToDelete = currFolder.subfolders[path[currIndex]] ?: return false

            folderToDelete.deconstruct()
            currFolder.subfolders.remove(path[currIndex])

            return true
        }
        else{
            var nextFolder = currFolder.subfolders[path[currIndex]] ?: return false
            return unlinkFolder(nextFolder, currIndex + 1, path)
        }
    }


    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        if(args.size != 1){
            ErrorMessages.argLength(player, args.size, 1)
            return false
        }

        if(args[0] == "root")
            main.rootFolder.deconstruct()
        else if(!unlinkFolder(main.rootFolder, 0, args[0].split("."))){
            ErrorMessages.invalid(player, "folder ${args[0]}")
            return true
        }

        InfoMessages.success(player)

        return true
    }
}