package com.fivevl.skymines.commands

import com.fivevl.skymines.Compressors
import com.fivevl.skymines.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.StringJoiner

class CompressorCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Utils.hex("&cYou can only run this command as a player!"))
            return true
        }
        if (!sender.hasPermission("sm.bypass")) {
            sender.sendMessage(Utils.hex("&cYou do not have permission to execute this command!"))
            return true
        }
        if (args.size != 1) {
            sender.sendMessage(Utils.hex("&cCorrect command usage: /compressor [compressor type]"))
            return true
        }
        val comp: Compressors?
        try {
            comp = Compressors.valueOf("${args[0].uppercase()}_COMPRESSOR")
        } catch (ex: IllegalArgumentException) {
            val joiner = StringJoiner("\n")
            for (c in Compressors.values()) {
                joiner.add("&c" + c.name.substring(0, c.name.length - 11))
            }
            sender.sendMessage(Utils.hex("&cThat is not a valid compressor! \n&cCorrect compressors: \n${joiner}"))
            return true
        }
        sender.inventory.addItem(comp.item)
        return true
    }
}