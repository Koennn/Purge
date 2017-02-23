package me.koenn.purge.listeners;

import me.koenn.purge.Purge;
import me.koenn.purge.util.ActionBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class KillListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (Purge.activePurge == null || !event.getEntity().getType().equals(EntityType.PLAYER)) {
            return;
        }

        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }

        Map<Player, Integer> scores = Purge.activePurge.getScores();
        scores.put(killer, scores.get(killer) + 1);

        ActionBar actionBar = new ActionBar(Purge.getReferences().ACTIONBAR_KILL_MESSAGE);
        actionBar.setStay(3);
        actionBar.send(killer);
    }
}
