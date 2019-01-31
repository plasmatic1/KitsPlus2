package me.tlwv2.kitsp2

import me.tlwv2.kitsp2.defs.KitKeys
import me.tlwv2.kitsp2.defs.Serialized
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

/*
This function creates the default icon for kits.  This is because icons cannot be set
on the creation of kits (too messy) and have to be set after.  So until then the icon is
just a stone block with the kit's name as its icon.
 */
fun defaultIcon(name: String): ItemStack{
    var icon = ItemStack(Material.STONE)
    var meta = Bukkit.getItemFactory().getItemMeta(Material.STONE)

    meta.displayName = name
    icon.itemMeta = meta

    return icon
}

@Suppress("UNCHECKED_CAST")
class Kit() : ConfigurationSerializable{
    lateinit var icon: ItemStack
    var inventory: Array<ItemStack>? = null
    var effects: Array<PotionEffect>? = null

    constructor(name: String) : this(){
        icon = defaultIcon(name)
    }

    /*
    This "applies" this kit to the supplied player, in essence loading the kit onto them
     */
    fun apply(player: Player){
        if (this.inventory != null)
            player.inventory.contents = this.inventory
        else
            player.inventory.clear()

        player.updateInventory()

        for(effect in player.activePotionEffects)
            player.removePotionEffect(effect.type)

        if (this.effects != null)
            player.addPotionEffects(this.effects!!.asList())
    }

    /*
    Serialization function
     */
    constructor(map: Serialized) : this(){
        this.icon = map[KitKeys.ICON] as ItemStack
        this.inventory = (map[KitKeys.INVENTORY] as List<ItemStack>?)?.toTypedArray()
        this.effects = (map[KitKeys.EFFECTS] as List<PotionEffect>?)?.toTypedArray()
    }

    override fun serialize(): Serialized {
        var map: Serialized = mutableMapOf()

        map[KitKeys.ICON] = this.icon as Any
        map[KitKeys.INVENTORY] = this.inventory as Any?
        map[KitKeys.EFFECTS] = this.effects as Any?

        return map
    }
}