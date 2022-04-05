package com.fivevl.skymines

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

enum class Scrolls(val item: ItemStack, val location: Location) {
    ICE_SCROLL(getScroll("Ice"), Location(Bukkit.getWorld("world"), -165.5, 82.0, -52.5, 0F, 0F)),
    DESERT_SCROLL(getScroll("Desert"), Location(Bukkit.getWorld("world"), -256.5, 92.0, -78.5, 180F, 0F))
}
@Suppress("deprecation")
fun getScroll(name: String): ItemStack {
    val item = ItemStack(Material.PAPER)
    val meta = item.itemMeta
    meta.setDisplayName(Utils.hex("&bTravel Scroll - $name"))
    meta.addEnchant(Enchantment.DURABILITY, 1, true)
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
    item.itemMeta = meta
    return item
}