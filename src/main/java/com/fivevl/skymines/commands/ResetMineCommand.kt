package com.fivevl.skymines.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import com.fivevl.skymines.Utils
import java.util.*


class ResetMineCommand : CommandExecutor {
    private val resetMineCooldown = HashMap<Player, Long>()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.hasPermission("sm.mines.reset")) {
            sender.sendMessage(Utils.hex("&cYou do not have permission to execute this command!"))
            return true
        }
        if (sender is Player && resetMineCooldown.containsKey(sender) && resetMineCooldown[sender]?.let {
                Utils.hasCooldown(
                    it
                )
            } == true) {
            sender.sendMessage(Utils.hex("&cThis command is on cooldown!"))
            return true
        }
        val mineNames = StringJoiner(" ")
        for (name in Utils.mines.keys) {
            mineNames.add(name)
        }
        if (args.size != 1) {
            sender.sendMessage(Utils.hex("&cCorrect command usage: /resetmine [mine] \n&cAll correct mines: $mineNames"))
            return true
        }
        if (!Utils.mines.containsKey(args[0])) {
            sender.sendMessage(Utils.hex("&cThat is not a correct mine! \n&cAll correct mines: $mineNames"))
            return true
        }
        Utils.mines[args[0]]?.resetMine()
        sender.sendMessage(Utils.hex("&aSuccessfully reset mine '" + args[0] + "'!"))
        if (sender is Player && !sender.hasPermission("sm.mines.reset.bypass")) {
            resetMineCooldown[sender] = System.currentTimeMillis() + (60 * 1000)
        }
        return true
    }
}
