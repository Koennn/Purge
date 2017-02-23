package me.koenn.purge.listeners;

import me.koenn.purge.Purge;
import me.koenn.purge.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && UpdateChecker.updateAvailable()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Purge.getInstance(), () -> UpdateChecker.sendUpdateMessage(player), 10);
        }
    }
}
