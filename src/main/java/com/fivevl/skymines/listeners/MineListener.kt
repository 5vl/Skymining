package com.fivevl.skymines.listeners

import com.fivevl.skymines.Compressors
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import com.fivevl.skymines.Utils
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt


class MineListener : Listener {
    @EventHandler
    fun onMine(e: BlockBreakEvent) {
        val p = e.player
        if (p.inventory.firstEmpty() == -1) {
            p.sendMessage(Utils.hex("&cYour inventory is full!"))
            e.isCancelled = true
            return
        }
        val blockType = e.block.type
        var cancel = false
        when (p.inventory.itemInMainHand.type) {
            Material.WOODEN_PICKAXE -> {
                when (blockType) {
                    Material.STONE, Material.COBBLESTONE, Material.COAL_ORE, Material.COAL_BLOCK -> {}
                    else -> cancel = true
                }
            }
            Material.STONE_PICKAXE -> {
                when (blockType) {
                    Material.STONE, Material.COBBLESTONE, Material.COAL_ORE, Material.COAL_BLOCK, Material.GOLD_ORE, Material.GOLD_BLOCK -> {}
                    else -> cancel = true
                }
            }
            Material.GOLDEN_PICKAXE -> {
                when (blockType) {
                    Material.STONE, Material.COBBLESTONE, Material.COAL_ORE, Material.COAL_BLOCK, Material.GOLD_ORE, Material.GOLD_BLOCK, Material.IRON_ORE, Material.IRON_BLOCK -> {}
                    else -> cancel = true
                }
            }
            Material.IRON_PICKAXE -> {
                when (blockType) {
                    Material.STONE, Material.COBBLESTONE, Material.COAL_ORE, Material.COAL_BLOCK, Material.GOLD_ORE, Material.GOLD_BLOCK, Material.IRON_ORE, Material.IRON_BLOCK, Material.DIAMOND_ORE, Material.DIAMOND_BLOCK -> {}
                    else -> cancel = true
                }
            }
            Material.DIAMOND_PICKAXE -> {
                when (blockType) {
                    Material.STONE, Material.COBBLESTONE, Material.COAL_ORE, Material.COAL_BLOCK, Material.GOLD_ORE, Material.GOLD_BLOCK, Material.IRON_ORE, Material.IRON_BLOCK, Material.DIAMOND_ORE, Material.DIAMOND_BLOCK, Material.EMERALD_ORE, Material.EMERALD_BLOCK, Material.REDSTONE_ORE, Material.REDSTONE_BLOCK, Material.LAPIS_ORE, Material.LAPIS_BLOCK -> {}
                    else -> cancel = true
                }
            }
            Material.NETHERITE_PICKAXE -> {
                cancel = blockType == Material.SNOW_BLOCK
            }
            Material.WOODEN_SHOVEL -> {
                cancel = blockType != Material.SNOW_BLOCK
            }
            else -> cancel = true
        }
        if (cancel && !p.hasPermission("sm.bypass")) {
            e.isCancelled = true
            return
        }
        if (!cancel) {
            calculateItems(p, blockType)
        }
    }

    private fun calculateItems(p: Player, inBlock: Material) {
        var block = inBlock
        val hand = p.inventory.itemInMainHand
        var amount = 1.0
        val compressor: Compressors
        if (hand.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            val level = hand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS)
            amount *= level.toDouble().pow(1.01)
            val rand = Random()
            amount = if (rand.nextInt(100) < 50) {
                amount + 0.5
            } else {
                amount - 0.5
            }
        }
        when (block) {
            Material.STONE -> {
                compressor = Compressors.STONE_COMPRESSOR
            }
            Material.COAL_ORE -> {
                block = Material.COAL
                compressor = Compressors.COAL_COMPRESSOR
            }
            Material.GOLD_ORE -> {
                block = Material.RAW_GOLD
                compressor = Compressors.GOLD_COMPRESSOR
            }
            Material.IRON_ORE -> {
                block = Material.RAW_IRON
                compressor = Compressors.IRON_COMPRESSOR
            }
            Material.DIAMOND_ORE -> {
                block = Material.DIAMOND
                compressor = Compressors.DIAMOND_COMPRESSOR
            }
            Material.EMERALD_ORE -> {
                block = Material.EMERALD
                compressor = Compressors.EMERALD_COMPRESSOR
            }
            Material.REDSTONE_ORE -> {
                block = Material.REDSTONE
                amount *= 2.0
                compressor = Compressors.REDSTONE_COMPRESSOR
            }
            Material.LAPIS_ORE -> {
                block = Material.LAPIS_LAZULI
                amount *= 2.0
                compressor = Compressors.LAPIS_COMPRESSOR
            }
            Material.BLUE_ICE -> {
                compressor = Compressors.ICE_COMPRESSOR
            }
            Material.SNOW_BLOCK -> {
                compressor = Compressors.SNOW_COMPRESSOR
            }
            else -> {
                val str = block.toString()
                if (str.endsWith("_BLOCK") || str == "STONE" || str == "COBBLESTONE") {
                    if (str == "GRASS_BLOCK") return
                    p.inventory.addItem(ItemStack(block, 1))
                    return
                } else return
            }
        }
        getCompressedItem(p, compressor, block, amount)
    }

    private fun getCompressedItem(p: Player, comp: Compressors?, block: Material, amount: Double) {
        p.inventory.addItem(ItemStack(block, amount.roundToInt()))
        if (comp == null) return
        if (p.inventory.contains(block) && p.inventory.contains(comp.item)) {
            for (i in p.inventory.contents) {
                if (i != null && i.amount >= 64 && i.type == block && !i.itemMeta.hasEnchants()) {
                    p.inventory.remove(ItemStack(block, 64))
                    p.inventory.addItem(comp.compress.item)
                }
            }
        }
    }
}
