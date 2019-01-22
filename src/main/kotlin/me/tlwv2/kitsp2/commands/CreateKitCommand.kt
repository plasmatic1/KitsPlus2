package me.tlwv2.kitsp2.commands

import me.tlwv2.kitsp2.Kit
import me.tlwv2.kitsp2.Main
import me.tlwv2.kitsp2.commands.messages.ErrorMessages
import me.tlwv2.kitsp2.commands.messages.InfoMessages
import me.tlwv2.kitsp2.defs.Perms
import org.bukkit.entity.Player

class CreateKitCommand(main: Main) : KitsPlusCommand(Perms.CREATE_KIT, main) {
    override fun onPlayerCommand(player: Player, args: Array<out String>): Boolean {
        if(args.size != 1){
            ErrorMessages.argLength(player, args.size, 1)
            return true
        }

        if(main.searchKit(args[0]) != null){
            ErrorMessages.kitExists(player, args[0])
            return true
        }

        val kit = Kit(args[0])

        main.kitIconMap[kit.icon] = kit
        main.unusedKits[args[0]] = kit

        InfoMessages.success(player)

        return true
    }
}