package me.koenn.purge.util;

import me.koenn.purge.Purge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
//TODO: Rework class to be dynamic.
public class FancyBoard {

    public static final List<FancyBoard> fancyBoards = new ArrayList<>();

    private Scoreboard scoreboard;
    private Objective objective;
    private Player player;
    private int task;

    public FancyBoard(Player player) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.player = player;
        fancyBoards.add(this);
        this.refreshBoard(false);
    }

    public static void disableAll() {
        fancyBoards.forEach(FancyBoard::disable);
    }

    public void refreshBoard(boolean unregister) {
        if (!this.player.isOnline() || Purge.activePurge == null) {
            this.disable();
            return;
        }
        if (unregister) {
            this.objective.unregister();
        }
        this.objective = this.scoreboard.registerNewObjective("info", "dummy");
        this.objective.setDisplayName(translateAlternateColorCodes('&', "&8> &4&lPurge &8<"));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        List<String> board = Purge.getConfigManager().getList("lines", "scoreboard");
        int index = board.size() - 1;
        Player player1 = PurgeHelper.getTopScorer(1);
        Player player2 = PurgeHelper.getTopScorer(2);
        Player player3 = PurgeHelper.getTopScorer(3);
        for (String line : board) {
            this.objective.getScore(line
                    .replace("%player1%", player1 != null ? player1.getName() : "")
                    .replace("%score1%", player1 != null ? String.valueOf(Purge.activePurge.getScores().get(player1)) : "")

                    .replace("%player2%", player2 != null ? player2.getName() : "")
                    .replace("%score2%", player2 != null ? String.valueOf(Purge.activePurge.getScores().get(player2)) : "")

                    .replace("%player3%", player3 != null ? player3.getName() : "")
                    .replace("%score3%", player3 != null ? String.valueOf(Purge.activePurge.getScores().get(player3)) : "")

                    .replace("%selfScore%", String.valueOf(Purge.activePurge.getScores().get(player)))
            ).setScore(index);
            index--;
        }
    }

    public void set() {
        this.player.setScoreboard(this.scoreboard);
        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Purge.getInstance(), () -> this.refreshBoard(true), 0, 50);
    }

    public void disable() {
        Bukkit.getScheduler().cancelTask(this.task);
        this.objective.unregister();
    }
}
