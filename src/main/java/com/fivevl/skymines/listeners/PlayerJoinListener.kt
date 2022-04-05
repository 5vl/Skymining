package com.fivevl.skymines.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import com.fivevl.skymines.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.io.File


class PlayerJoinListener : Listener {
    @EventHandler
    @Suppress("deprecation")
    fun onJoin(e: PlayerJoinEvent) {
        e.player.teleport(Utils.spawn)
        val file = File(Utils.mainInstance?.dataFolder, "playerdata.yml")
        val config = YamlConfiguration.loadConfiguration(file)
        if (config.getConfigurationSection(e.player.uniqueId.toString()) == null) {
            config.createSection(e.player.uniqueId.toString())
            e.player.inventory.addItem(ItemStack(Material.COOKED_BEEF, 16), getPick())
            config.save(file)
            Bukkit.broadcastMessage(Utils.hex("&eWelcome ${e.player.name} to the server!"))
        }
        e.joinMessage(null)
    }

    private fun getPick(): ItemStack {
        val item = ItemStack(Material.WOODEN_PICKAXE)
        val meta = item.itemMeta
        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        item.itemMeta = meta
        return item
    }

    @EventHandler
    fun onLeave(e: PlayerQuitEvent) {
        e.quitMessage(null)
    }
}
