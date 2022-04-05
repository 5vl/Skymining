package com.fivevl.skymines

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

enum class Items(val item: ItemStack) {
    COMPRESSED_STONE(getCompressedItem(Material.STONE, "&7Compressed Stone")),
    COMPRESSED_COAL(getCompressedItem(Material.COAL, "&8Compressed Coal")),
    COMPRESSED_GOLD(getCompressedItem(Material.GOLD_INGOT, "&6Compressed Gold")),
    COMPRESSED_IRON(getCompressedItem(Material.IRON_INGOT, "&7Compressed Iron")),
    COMPRESSED_DIAMOND(getCompressedItem(Material.DIAMOND, "&bCompressed Diamond")),
    COMPRESSED_EMERALD(getCompressedItem(Material.EMERALD, "&aCompressed Emerald")),
    COMPRESSED_REDSTONE(getCompressedItem(Material.REDSTONE, "&cCompressed Redstone")),
    COMPRESSED_LAPIS(getCompressedItem(Material.LAPIS_LAZULI, "&3Compressed Lapis")),
    COMPRESSED_ICE(getCompressedItem(Material.PACKED_ICE, "&bCompressed Ice")),
    COMPRESSED_SNOW(getCompressedItem(Material.SNOW_BLOCK, "&fCompressed Snow"))
}

@Suppress("deprecation")
private fun getCompressedItem(mat: Material, name: String): ItemStack {
    val item = ItemStack(mat)
    val meta = item.itemMeta
    meta.setDisplayName(Utils.hex(name))
    meta.addEnchant(Enchantment.DURABILITY, 1, true)
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
    item.itemMeta = meta
    return item
}
