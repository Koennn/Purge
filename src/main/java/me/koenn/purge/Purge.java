package me.koenn.purge;

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

    private static ConfigManager configManager;

    @Override
    public void onEnable() {
        this.getLogger().info("All credits for this plugin go to Koenn");

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
