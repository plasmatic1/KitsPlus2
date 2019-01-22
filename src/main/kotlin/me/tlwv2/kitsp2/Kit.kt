package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.defs.KitKeys
import me.tlwv2.kitsp2.defs.Serialized
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

fun defaultIcon(name: String): ItemStack{
    var icon = ItemStack(Material.STONE)
    var meta = icon.itemMeta

    meta.displayName = name
    icon.itemMeta = meta

    return icon
}

class Kit() : ConfigurationSerializable{
    lateinit var icon: ItemStack
    lateinit var inventory: Array<ItemStack>
    lateinit var effects: Array<PotionEffect>

    constructor(name: String) : this(){
        icon = defaultIcon(name)
    }

    constructor(map: Serialized) : this(){
        this.icon = map[KitKeys.ICON] as ItemStack
        this.inventory = map[KitKeys.INVENTORY] as Array<ItemStack>
        this.effects = map[KitKeys.EFFECTS] as Array<PotionEffect>
    }

    fun apply(player: Player){
        player.inventory.contents = this.inventory
        player.activePotionEffects.clear()
        player.addPotionEffects(this.effects.asList())
    }

    override fun serialize(): Serialized {
        var map: Serialized = mutableMapOf()

        map[KitKeys.ICON] = this.icon as Object
        map[KitKeys.INVENTORY] = this.inventory as Object
        map[KitKeys.EFFECTS] = this.effects as Object

        return map
    }
}