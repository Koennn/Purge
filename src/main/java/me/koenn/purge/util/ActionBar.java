/*
 * Copyright (c) StaticMC Server - 2015 - Sotanna
 */

package me.koenn.purge.util;

import me.koenn.purge.Purge;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class ActionBar {

    private String message;
    private int stay = 0;

    public ActionBar(String message) {
        this.message = translateAlternateColorCodes('&', message);
    }

    public ActionBar setStay(int stay) {
        this.stay = stay;
        return this;
    }

    public void send(Player p) {
        new BukkitRunnable() {
            int time = 0;

            @Override
            public void run() {
                if (time >= stay) {
                    cancel();
                    return;
                }
                IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
                PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
                time++;
            }
        }.runTaskTimer(Purge.getInstance(), 0L, 20L);
    }

    public void sendAll() {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }
}
