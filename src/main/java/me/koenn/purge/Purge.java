package me.koenn.purge;

import me.koenn.purge.commands.PurgeCommand;
import me.koenn.purge.listeners.JoinListener;
import me.koenn.purge.listeners.KillListener;
import me.koenn.purge.util.ConfigManager;
import me.koenn.purge.util.FancyBoard;
import me.koenn.purge.util.References;
import me.koenn.purge.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A Minecraft PvP purge plugin
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public final class Purge extends JavaPlugin {

    public static PurgeController activePurge;

    private static ConfigManager configManager;
    private static Purge instance;
    private static References references;

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static Purge getInstance() {
        return instance;
    }

    public static References getReferences() {
        return references;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.getLogger().info("All credits for this plugin go to Koenn");

        configManager = new ConfigManager(this);
        references = new References();
        if (!references.load()) {
            Purge.getInstance().getLogger().severe("Error while loading configurations!");
            Purge.getInstance().getLogger().severe("The plugin will now disable, please fix any mistake in the config.yml file!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.getCommand("purge").setExecutor(new PurgeCommand());

        Bukkit.getPluginManager().registerEvents(new KillListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            if (UpdateChecker.updateAvailable()) {
                this.getLogger().info("An update is available! Download it here: https://github.com/Koennn/Purge");
                Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp).forEach(UpdateChecker::sendUpdateMessage);
            }
        }, 40);

        this.getLogger().info("The plugin has been successfully setup, we're ready to start the Purge!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Stopping time check task...");
        Bukkit.getScheduler().cancelTasks(this);

        this.getLogger().info("Disabling scoreboard...");
        FancyBoard.disableAll();
    }
}
