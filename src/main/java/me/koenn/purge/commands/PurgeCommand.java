package me.koenn.purge.commands;

import me.koenn.purge.Purge;
import me.koenn.purge.PurgeController;
import me.koenn.purge.gui.PrizeGui;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class PurgeCommand implements CommandExecutor {

    public static final String START_USAGE = "Usage: /purge start <duration>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            PluginDescriptionFile description = Purge.getInstance().getDescription();
            sender.sendMessage(ChatColor.AQUA + description.getName() + " v" + description.getVersion() + " by " + description.getAuthors().get(0));
            sender.sendMessage(ChatColor.AQUA + "Use /purge help for a list of commands");
            return true;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "start":
                if (!sender.hasPermission("purge.start")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage(START_USAGE);
                    return true;
                }

                int duration;
                try {
                    duration = Integer.parseInt(args[1]);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(START_USAGE);
                    return true;
                }
                if (duration <= 0) {
                    sender.sendMessage(START_USAGE);
                    return true;
                }

                if (Purge.activePurge != null) {
                    sender.sendMessage(ChatColor.RED + "There already is a purge going on, please wait until it ends before starting a new one!");
                    return true;
                }

                PurgeController.preDuration = duration;

                PrizeGui gui = new PrizeGui();
                gui.open(((Player) sender));
                return true;

            case "help":
                if (!sender.hasPermission("purge.help")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
                    return true;
                }

                for (String line : Purge.getReferences().HELP_MESSAGE.split("%")) {
                    sender.sendMessage(translateAlternateColorCodes('&', line));
                }
                return true;

            case "time":
                if (Purge.activePurge == null) {
                    sender.sendMessage(translateAlternateColorCodes('&', Purge.getReferences().NO_PURGE));
                    return true;
                }

                sender.sendMessage(
                        translateAlternateColorCodes('&', Purge.getReferences().PURGE_TIME_LEFT.replace("%time%", String.valueOf(Purge.activePurge.getDuration())))
                );
                return true;

            default:
                return false;
        }
    }
}
