package com.fivevl.skymines.commands

import com.fivevl.skymines.Scrolls
import com.fivevl.skymines.Utils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.StringJoiner

class ScrollCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("sm.givescroll")) {
            sender.sendMessage(Utils.hex("&cYou do not have permission to execute this command!"))
            return true
        }
        if (args.size != 3) {
            sender.sendMessage(Utils.hex("&cCorrect command usage: /scroll [player] [scroll] [amount]"))
            return true
        }
        val gp = Bukkit.getPlayer(args[0])
        if (gp == null) {
            sender.sendMessage(Utils.hex("&cThat is not a correct player!"))
            return true
        }
        var scroll: Scrolls? = null
        for (name in Scrolls.values()) {
            if (args[1].uppercase() == name.toString().substring(0, name.toString().length - 7)) {
                scroll = name
                break
            }
        }
        if (scroll == null) {
            val join = StringJoiner("\n")
            for (name in Scrolls.values()) {
                join.add(name.toString().substring(0, name.toString().length - 7))
            }
            sender.sendMessage(Utils.hex("&cThat is not a correct scroll! Correct scrolls:\n$join"))
            return true
        }
        val amount: Int
        try {
            amount = args[2].toInt()
        } catch (e: NumberFormatException) {
            sender.sendMessage(Utils.hex("&cThat is not a correct amount!"))
            return true
        }
        val item = scroll.item
        item.amount = amount
        gp.inventory.addItem(item)
        return true
    }
}