package com.fivevl.skymines

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.regex.Pattern


object Utils {
    var mainInstance: Main? = null
    var mines: HashMap<String, Mine> = HashMap()
    var spawn = Location(Bukkit.getWorld("world"), 0.5, 73.0, 0.5, 180F, 0F)
    fun hex(s: String): String {
        var s2 = s
        val pattern = Pattern.compile("#[a-fA-F0-9]{6}")
        var match = pattern.matcher(s)
        while (match.find()) {
            val color = s.substring(match.start(), match.end())
            s2 = s2.replace(color, ChatColor.of(color).toString() + "")
            match = pattern.matcher(s2)
        }
        return ChatColor.translateAlternateColorCodes('&', s2)
    }

    fun hasCooldown(time: Long): Boolean {
        return time < System.currentTimeMillis()
    }
}
