package com.fivevl.skymines

import com.fivevl.skymines.commands.CompressorCommand
import com.fivevl.skymines.commands.ResetMineCommand
import com.fivevl.skymines.commands.ScrollCommand
import com.fivevl.skymines.commands.SpawnCommand
import com.fivevl.skymines.listeners.*
import com.fivevl.skymines.tabcomplete.ResetMineTabComplete
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.util.stream.Collectors


class Main : JavaPlugin() {
    override fun onEnable() {
        Utils.mainInstance = this
        if (dataFolder.mkdir()) {
            logger.info("Data folder created!")
        }
        createPlayerData()
        registerMines()
        registerCommands()
        registerListeners()
    }

    private fun registerCommands() {
        getCommand("resetmine")?.setExecutor(ResetMineCommand())
        getCommand("resetmine")?.tabCompleter = ResetMineTabComplete()
        getCommand("spawn")?.setExecutor(SpawnCommand())
        getCommand("compressor")?.setExecutor(CompressorCommand())
        getCommand("scroll")?.setExecutor(ScrollCommand())
    }

    private fun registerListeners() {
        val plm = Bukkit.getPluginManager()
        plm.registerEvents(MineListener(), this)
        plm.registerEvents(PlayerJoinListener(), this)
        plm.registerEvents(PlayerMoveListener(), this)
        plm.registerEvents(CommandBlock(), this)
        plm.registerEvents(ClickListener(), this)
    }

    private fun createPlayerData() {
        val file = File(dataFolder, "playerdata.yml")
        try {
            if (file.createNewFile()) {
                logger.info("Created new playerdata.yml file.")
            }
        } catch (e: IOException) {
            logger.info("Could not create new playerdata.yml file.")
            e.printStackTrace()
        }
    }

    private fun registerMines() {
        createMinesYaml()
        val file = File(dataFolder, "mines.yml")
        val yamlConfig = YamlConfiguration.loadConfiguration(file)
        val minesList: List<ConfigurationSection?> =
            yamlConfig.getConfigurationSection("")?.getKeys(false)?.stream()
                ?.map(yamlConfig::getConfigurationSection)?.collect(Collectors.toList()) as List<ConfigurationSection?>
        for (sect in minesList) {
            if (!sect!!.name.equals("ExampleMine", ignoreCase = true)) {
                val world: World? = sect.getString("World")?.let { Bukkit.getWorld(it) }
                val corner1 = sect.getConfigurationSection("Corner1")
                val corner2 = sect.getConfigurationSection("Corner2")
                assert(corner1 != null)
                val loc1 = Location(world, corner1!!.getInt("x").toDouble(), corner1.getInt("y").toDouble(), corner1.getInt("z").toDouble()
                )
                assert(corner2 != null)
                val loc2 = Location(world, corner2!!.getInt("x").toDouble(), corner2.getInt("y").toDouble(), corner2.getInt("z").toDouble()
                )
                val mat1 = sect.getString("75PercentOfMine")?.let { Material.matchMaterial(it) }
                val mat2 = sect.getString("25PercentOfMine")?.let { Material.matchMaterial(it) }
                Utils.mines[sect.name] = Mine(loc1, loc2, mat1!!, mat2!!)
            }
        }
        autoResetMines()
    }

    private fun autoResetMines() {
        val sched = Bukkit.getScheduler()
        sched.scheduleSyncRepeatingTask(this, {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                for (mine in Utils.mines.values) {
                    mine.resetMine()
                }
            }
        }, 0, (15 * 20).toLong())
    }

    private fun createMinesYaml() {
        val file = File(dataFolder, "mines.yml")
        try {
            if (file.createNewFile()) {
                logger.info("Created new mines.yml file.")
                val yamlConfig = YamlConfiguration.loadConfiguration(file)
                val exampleMineSect = yamlConfig.createSection("ExampleMine")
                exampleMineSect["World"] = "world"
                val corner1Sect = exampleMineSect.createSection("Corner1")
                corner1Sect["x"] = 0
                corner1Sect["y"] = 80
                corner1Sect["z"] = 0
                val corner2Sect = exampleMineSect.createSection("Corner2")
                corner2Sect["x"] = 20
                corner2Sect["y"] = 100
                corner2Sect["z"] = 20
                exampleMineSect["75PercentOfMine"] = "STONE"
                exampleMineSect["25PercentOfMine"] = "COBBLESTONE"
                yamlConfig.save(file)
            }
        } catch (e: IOException) {
            logger.severe("Could not create new mines.yml file.")
            e.printStackTrace()
        }
    }

}