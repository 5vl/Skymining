package com.fivevl.skymines.tabcomplete

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import com.fivevl.skymines.Utils


class ResetMineTabComplete : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        val list = ArrayList<String>()
        if (sender.hasPermission("sm.mines.reset")) {
            for (mines in Utils.mines.keys) {
                list.add(mines)
            }
        }
        return list
    }
}
