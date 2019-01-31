package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.commands.messages.ErrorMessages
import me.tlwv2.kitsp2.commands.messages.InfoMessages
import me.tlwv2.kitsp2.defs.Chat
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class UnlinkKitCommand(main: Main) : KitsPlusCommand(Perms.UNLINK_KIT, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        if(args.size != 1){
            ErrorMessages.argLength(player, args.size, 1)
            return false
        }

        var kit = main.rootFolder.searchKit(args[0])

        if(kit == null){
            ErrorMessages.kitDoesNotExist(player, args[0])
            return true
        }

        main.unusedKits[args[0]] = kit
        main.rootFolder.removeKit(args[0])

        InfoMessages.success(player)

        return true
    }
}