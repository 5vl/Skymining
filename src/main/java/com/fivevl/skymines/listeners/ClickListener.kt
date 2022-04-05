package com.fivevl.skymines.listeners

import com.fivevl.skymines.Scrolls
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class ClickListener : Listener {
    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        if (e.hand != EquipmentSlot.HAND) return
        val p = e.player
        for (scroll in Scrolls.values()) {
            if (p.inventory.itemInMainHand.isSimilar(scroll.item)) {
                p.teleport(scroll.location)
                val new = scroll.item
                new.amount = p.inventory.itemInMainHand.amount - 1
                p.inventory.setItemInMainHand(new)
                break
            }
        }
    }
}