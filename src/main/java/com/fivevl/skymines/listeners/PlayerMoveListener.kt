package com.fivevl.skymines.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import com.fivevl.skymines.Utils


class PlayerMoveListener : Listener {
    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        val p = e.player
        if (p.location.y <= 0) {
            p.teleport(Utils.spawn)
        }
    }
}
