package me.tlwv2.kitsp2.commands.messages

import me.tlwv2.kitsp2.defs.Chat
import org.bukkit.entity.Player

object ErrorMessages{
    // ----------------------------------------------
    // Generates and sends many common error messages
    // ----------------------------------------------

    fun argLength(player: Player, length: Int, expectedLength: Int) =
        player.sendMessage("${Chat.RED}Invalid Arguments! You passed $length arguments when $expectedLength was/were required!")

    fun kitDoesNotExist(player: Player, invalidName: String) = player.sendMessage("${Chat.RED}Invalid kit \"$invalidName\"!")

    fun kitExists(player: Player, invalidName: String) = player.sendMessage("${Chat.RED}Kit \"$invalidName\" already exists!")

    fun invalid(player: Player, invalid: String) = player.sendMessage("${Chat.RED}Invalid $invalid!")

    fun invalidChoice(player: Player, invalidChoice: String) = player.sendMessage("${Chat.RED}Invalid choice $invalidChoice!")
}

object InfoMessages{
    // ---------------------------------------------
    // Generates and sends many common info messages
    // ---------------------------------------------

    fun success(player: Player) = player.sendMessage("${Chat.GREEN}Success!")
}