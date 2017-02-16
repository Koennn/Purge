package me.koenn.purge;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class PurgeController {

    public static String PURGE_STARTING_TITLE;
    public static String PURGE_STARTING_SUBTITLE;
    public static String PURGE_STARTED_TITLE;
    public static String PURGE_ENDED_TITLE;
    public static String PURGE_STARTED_SUBTITLE;
    public static String PURGE_ENDED_SUBTITLE;

    private List<ProtectedRegion> noPvpRegions = new ArrayList<>();
    private List<ProtectedRegion> invincibleRegions = new ArrayList<>();
    private int duration;
    private int taskId;
    private int countdown;

    public PurgeController(int duration) {
        this.duration = duration;

        PURGE_STARTING_TITLE = ChatColor.DARK_RED + "" + ChatColor.BOLD + "WARNING";
        PURGE_STARTING_SUBTITLE = ChatColor.RED + Purge.getConfigManager().getString("purge_starting", "messages");
        PURGE_STARTED_TITLE = ChatColor.DARK_RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_started_large", "messages");
        PURGE_ENDED_TITLE = ChatColor.DARK_RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_ended_large", "messages");
        PURGE_STARTED_SUBTITLE = ChatColor.RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_started_small", "messages");
        PURGE_ENDED_SUBTITLE = ChatColor.RED + "" + ChatColor.BOLD + Purge.getConfigManager().getString("purge_ended_small", "messages");

        Purge.activePurge = this;
    }

    public void start() {
        for (World world : Bukkit.getWorlds()) {
            Purge.getInstance().getLogger().info("Preparing purge for world \'" + world.getName() + "\'...");
            for (ProtectedRegion region : WorldGuardPlugin.inst().getRegionManager(world).getRegions().values()) {
                for (Flag flag : region.getFlags().keySet()) {
                    if (flag.getName().equalsIgnoreCase("pvp") && region.getFlag(flag).equals(StateFlag.State.DENY)) {
                        Purge.getInstance().getLogger().info(flag.getName() + " " + region.getFlag(flag));
                        this.noPvpRegions.add(region);
                    } else if (flag.getName().equalsIgnoreCase("invincible") && region.getFlag(flag).equals(StateFlag.State.ALLOW)) {
                        this.invincibleRegions.add(region);
                    }
                }
            }
            Purge.getInstance().getLogger().info("Purge for world \'" + world.getName() + "\' is now ready to start!");
        }

        Purge.getInstance().getLogger().info("Waiting for countdown...");

        this.countdown = 20;
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Purge.getInstance(), () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                PacketPlayOutTitle init = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 0, 30, 10);
                connection.sendPacket(init);

                IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + PURGE_STARTING_TITLE + "\"}");
                PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title);
                connection.sendPacket(titlePacket);

                IChatBaseComponent subtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + PURGE_STARTING_SUBTITLE.replace("%time%", String.valueOf(this.countdown)) + "\"}");
                PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle);
                connection.sendPacket(subtitlePacket);
            }

            this.countdown--;
            if (this.countdown <= 0) {
                this.startPurge();
            }
        }, 20, 20);
    }

    public void startPurge() {
        Bukkit.getScheduler().cancelTask(this.taskId);

        for (World world : Bukkit.getWorlds()) {
            Purge.getInstance().getLogger().info("Starting purge for world \'" + world.getName() + "\'...");
            for (ProtectedRegion region : this.noPvpRegions) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rg flag -w " + world.getName() + " " + region.getId() + " pvp allow");
            }
            for (ProtectedRegion region : this.invincibleRegions) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rg flag -w " + world.getName() + " " + region.getId() + " invincible deny");
            }
            Purge.getInstance().getLogger().info("Purge for world \'" + world.getName() + "\' has now started!");
        }

        Purge.getInstance().getLogger().info("The purge has started!");
        Purge.getInstance().getLogger().info("Purging for " + this.duration + "seconds.");

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            PacketPlayOutTitle init = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 0, 120, 40);
            connection.sendPacket(init);

            IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + PURGE_STARTED_TITLE + "\"}");
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title);
            connection.sendPacket(titlePacket);

            IChatBaseComponent subtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + PURGE_STARTED_SUBTITLE + "\"}");
            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle);
            connection.sendPacket(subtitlePacket);
        }

        this.startPurgeEndTimer();
    }

    public void startPurgeEndTimer() {
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Purge.getInstance(), () -> {
            this.duration--;

            if (this.duration <= 0) {
                for (World world : Bukkit.getWorlds()) {
                    Purge.getInstance().getLogger().info("Ending purge for world \'" + world.getName() + "\'...");
                    for (ProtectedRegion region : this.noPvpRegions) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rg flag -w " + world.getName() + " " + region.getId() + " pvp deny");
                    }
                    for (ProtectedRegion region : this.invincibleRegions) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rg flag -w " + world.getName() + " " + region.getId() + " invincible allow");
                    }
                    Purge.getInstance().getLogger().info("Purge for world \'" + world.getName() + "\' has now ended!");
                }

                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                    PacketPlayOutTitle init = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 0, 120, 40);
                    connection.sendPacket(init);

                    IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + PURGE_ENDED_TITLE + "\"}");
                    PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title);
                    connection.sendPacket(titlePacket);

                    IChatBaseComponent subtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + PURGE_ENDED_SUBTITLE + "\"}");
                    PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle);
                    connection.sendPacket(subtitlePacket);
                }

                Purge.getInstance().getLogger().info("The purge has ended!");

                Purge.activePurge = null;
                Bukkit.getScheduler().cancelTask(this.taskId);
            }
        }, 0, 20);
    }

    public int getDuration() {
        return duration;
    }
}