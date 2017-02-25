package me.koenn.purge.gui;

import me.koenn.purge.Purge;
import me.koenn.purge.PurgeController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class PrizeGui implements Listener {

    private final Inventory inventory;

    public PrizeGui() {
        this.inventory = Bukkit.createInventory(null, 36, "Prizes");
        this.inventory.setItem(0, Purge.getReferences().FIRST_PRIZE_ICON);
        this.inventory.setItem(9, Purge.getReferences().SECOND_PRIZE_ICON);
        this.inventory.setItem(18, Purge.getReferences().THIRD_PRIZE_ICON);

        this.inventory.setItem(33, Purge.getReferences().CONFIRM_ITEM);
        this.inventory.setItem(29, Purge.getReferences().CANCEL_ITEM);

        Bukkit.getPluginManager().registerEvents(this, Purge.getInstance());
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getClickedInventory().equals(this.inventory)) {
            return;
        }

        if (event.getSlot() == 0 || event.getSlot() == 9 || event.getSlot() == 18) {
            event.setCancelled(true);
            return;
        }

        if (event.getSlot() == 29) {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            return;
        }

        if (event.getSlot() == 33) {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage(ChatColor.GREEN + "Starting a purge, please wait, this will take a few seconds!");

            ItemStack[][] prizes = new ItemStack[3][8];
            int index = 0;
            int place = 0;
            for (int i = 0; i < 27; i++) {
                if (i != 0 && i != 9 && i != 18) {
                    prizes[place][index] = this.inventory.getItem(i);
                    index++;
                    if (index == 8) {
                        index = 0;
                        place++;
                    }
                }
            }

            PurgeController purge = new PurgeController();
            purge.setPrizes(prizes);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Purge.getInstance(), purge::start, 30);
        }
    }
}
