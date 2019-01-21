package me.tlwv2.kitsp2.defs

class Perms{
    companion object{
        const val PERMISSION_INFO = "kitsp.use.pinfo"
    }
}

class Chat{
    companion object {
        const val RED = "§c"
        const val GOLD = "§6"
        const val GREY = "§7"

        const val NO_PERM_MESSAGE = "${RED}Insufficient Permissions!"
        const val NOT_PLAYER_MESSAGE = "${RED}Only a Player can execute this command!"
    }
}
