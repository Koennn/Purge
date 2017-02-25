package me.koenn.purge.util;

import me.koenn.purge.Purge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

    public ItemStack FIRST_PRIZE_ICON;
    public ItemStack SECOND_PRIZE_ICON;
    public ItemStack THIRD_PRIZE_ICON;
    public ItemStack CONFIRM_ITEM;
    public ItemStack CANCEL_ITEM;

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

        FIRST_PRIZE_ICON = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta meta = FIRST_PRIZE_ICON.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Prizes for first place");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "--------->");
        meta.setLore(lore);
        FIRST_PRIZE_ICON.setItemMeta(meta);

        SECOND_PRIZE_ICON = new ItemStack(Material.GOLD_BLOCK);
        meta = SECOND_PRIZE_ICON.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Prizes for second place");
        lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "--------->");
        meta.setLore(lore);
        SECOND_PRIZE_ICON.setItemMeta(meta);

        THIRD_PRIZE_ICON = new ItemStack(Material.IRON_BLOCK);
        meta = THIRD_PRIZE_ICON.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Prizes for third place");
        lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.STRIKETHROUGH + "--------->");
        meta.setLore(lore);
        THIRD_PRIZE_ICON.setItemMeta(meta);

        CONFIRM_ITEM = new ItemStack(Material.WOOL, 1, (short) 5);
        meta = CONFIRM_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Confirm and start purge");
        lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GREEN + "Click to confirm and start purge");
        meta.setLore(lore);
        CONFIRM_ITEM.setItemMeta(meta);

        CANCEL_ITEM = new ItemStack(Material.WOOL, 1, (short) 14);
        meta = CANCEL_ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Cancel");
        lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.RED + "Click to cancel");
        meta.setLore(lore);
        CANCEL_ITEM.setItemMeta(meta);
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
