package me.koenn.purge.commands;

import me.koenn.purge.PurgeController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "start":
                if (args.length < 2) {
                    sender.sendMessage(START_USAGE);
                    return true;
                }

                int duration = 0;
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

                PurgeController purge = new PurgeController(duration);
                purge.start();
                return true;

            default:
                return false;
        }
    }
}
