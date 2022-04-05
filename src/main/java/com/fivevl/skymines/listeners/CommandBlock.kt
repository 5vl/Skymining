package com.fivevl.skymines.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class CommandBlock : Listener {
    @EventHandler
    fun onBlockedCommand(e: PlayerCommandPreprocessEvent) {
        val cmds = arrayOf("/pl", "/bukkit:pl", "/minecraft:me", "/me")
        for (s in cmds) {
            if (e.message.startsWith(s)) {
                e.isCancelled = true
            }
        }
    }
}