package com.fivevl.skymines

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class Compressors(val item: ItemStack, val compress: Items) {
    STONE_COMPRESSOR(getCompressor("&7Stone"), Items.COMPRESSED_STONE),
    COAL_COMPRESSOR(getCompressor("&8Coal"), Items.COMPRESSED_COAL),
    GOLD_COMPRESSOR(getCompressor("&6Gold"), Items.COMPRESSED_GOLD),
    IRON_COMPRESSOR(getCompressor("&7Iron"), Items.COMPRESSED_IRON),
    DIAMOND_COMPRESSOR(getCompressor("&bDiamond"), Items.COMPRESSED_DIAMOND),
    EMERALD_COMPRESSOR(getCompressor("&aEmerald"), Items.COMPRESSED_EMERALD),
    REDSTONE_COMPRESSOR(getCompressor("&cRedstone"), Items.COMPRESSED_REDSTONE),
    LAPIS_COMPRESSOR(getCompressor("&3Lapis"), Items.COMPRESSED_LAPIS),
    ICE_COMPRESSOR(getCompressor("&bIce"), Items.COMPRESSED_ICE),
    SNOW_COMPRESSOR(getCompressor("&fSnow"), Items.COMPRESSED_SNOW)
}
@Suppress("deprecation")
private fun getCompressor(name: String): ItemStack {
    val item = ItemStack(Material.DISPENSER)
    val meta = item.itemMeta
    meta.setDisplayName(Utils.hex("$name Autocompressor"))
    item.itemMeta = meta
    return item
}