package me.koenn.purge;

import me.koenn.purge.commands.PurgeCommand;
import me.koenn.purge.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static Purge getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.getLogger().info("All credits for this plugin go to Koenn");

        this.getCommand("purge").setExecutor(new PurgeCommand());

        configManager = new ConfigManager(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("");
        }, 100, 1200);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Stopping time check task...");
        Bukkit.getScheduler().cancelTasks(this);
    }
}
