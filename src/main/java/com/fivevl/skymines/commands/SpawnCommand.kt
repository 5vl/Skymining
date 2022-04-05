package com.fivevl.skymines.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import com.fivevl.skymines.Utils


class SpawnCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Utils.hex("&cOnly players may execute this command!"))
            return true
        }
        if (!sender.hasPermission("sm.spawn")) {
            sender.sendMessage(Utils.hex("&cYou do not have permission to execute this command!"))
            return true
        }
        sender.teleport(Utils.spawn)
        sender.sendMessage(Utils.hex("&aYou have been teleported to spawn!"))
        return true
    }
}
