package com.fivevl.skymines

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.util.*


class Mine(private var loc1: Location, private var loc2: Location, private var b75: Material, private var b25: Material) {
    fun resetMine() {
        val rand = Random()
        for (x in loc1.blockX.coerceAtMost(loc2.blockX)..loc1.blockX.coerceAtLeast(loc2.blockX)) {
            for (y in loc1.blockY.coerceAtMost(loc2.blockY)..loc1.blockY.coerceAtLeast(loc2.blockY)) {
                for (z in loc1.blockZ.coerceAtMost(loc2.blockZ)..loc1.blockZ.coerceAtLeast(loc2.blockZ)) {
                    for (p in Bukkit.getOnlinePlayers()) {
                        val pLoc = p.location
                        if (pLoc.blockX == x && pLoc.blockY == y && pLoc.blockZ == z) {
                            p.teleport(pLoc.add(0.0, 1.0, 0.0))
                        }
                    }
                    val block = rand.nextInt(100)
                    if (block < 75) {
                        Location(Bukkit.getWorld("world"), x.toDouble(), y.toDouble(), z.toDouble()).block.type = b75
                    } else {
                        Location(Bukkit.getWorld("world"), x.toDouble(), y.toDouble(), z.toDouble()).block.type = b25
                    }
                }
            }
        }
    }
}
