package me.tlwv2.kitsp2

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
    var icon: ItemStack? = null
    var inventory: Array<ItemStack>? = null
    var effects: List<PotionEffect>? = null

    constructor(name: String) : this(){
        icon = defaultIcon(name)
    }

    constructor(map: Serialized) : this(){
        this.icon = map[KitKeys.ICON] as ItemStack
        this.inventory = map[KitKeys.INVENTORY] as Array<ItemStack>
        this.effects = map[KitKeys.EFFECTS] as List<PotionEffect>
    }

    fun apply(player: Player){
        TODO("not implemented")
    }

    override fun serialize(): Serialized {
        var map: Serialized = mutableMapOf()

        map[KitKeys.ICON] = this.icon as Object?
        map[KitKeys.INVENTORY] = this.inventory as Object?
        map[KitKeys.EFFECTS] = this.effects as Object?

        return map
    }
}