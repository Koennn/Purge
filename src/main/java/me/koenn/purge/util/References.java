package me.koenn.purge.util;

import me.koenn.purge.Purge;
import org.bukkit.ChatColor;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class References {

    public String VERSION_URL;
    public String ACTIONBAR_KILL_MESSAGE;
    public String HELP_MESSAGE;

    public String PURGE_STARTING_TITLE;
    public String PURGE_STARTING_SUBTITLE;
    public String PURGE_STARTED_TITLE;
    public String PURGE_ENDED_TITLE;
    public String PURGE_STARTED_SUBTITLE;
    public String PURGE_ENDED_SUBTITLE;
    public String PURGE_TIME_LEFT;
    public String NO_PURGE;

    public References() {
        VERSION_URL = "http://u5115p3268.web0091.zxcs.nl/versions/purge.json";
        PURGE_STARTING_TITLE = ChatColor.DARK_RED + "" + ChatColor.BOLD + "WARNING";
        HELP_MESSAGE = "" +
                "&b-===============================================-%" +
                "&6- &7/purge start <seconds>%" +
                "&7  Starts a purge that lasts a certain amount of seconds.%" +
                "&6- &7/purge time%" +
                "&7  Shows how much time is left before the purge ends.%" +
                "&6- &7/purge help%" +
                "&7  Shows this help menu.%" +
                "&b-===============================================-";
    }

    public boolean load() {
        try {
            ACTIONBAR_KILL_MESSAGE = Purge.getConfigManager().getString("kill_message", "messages");
            PURGE_STARTING_SUBTITLE = ChatColor.RED + Purge.getConfigManager().getString("purge_starting", "messages");
            PURGE_STARTED_TITLE = ChatColor.DARK_RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_started_large", "messages");
            PURGE_ENDED_TITLE = ChatColor.DARK_RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_ended_large", "messages");
            PURGE_STARTED_SUBTITLE = ChatColor.RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_started_small", "messages");
            PURGE_ENDED_SUBTITLE = ChatColor.RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_ended_small", "messages");
            PURGE_TIME_LEFT = Purge.getConfigManager().getString("time_left", "messages");
            NO_PURGE = Purge.getConfigManager().getString("no_purge", "messages");

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
