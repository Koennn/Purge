package me.koenn.purge.util;

import me.koenn.purge.Purge;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class PurgeHelper {

    public static Player getTopScorer(int place) {
        Map.Entry<Player, Integer> maxEntry = null;

        for (Map.Entry<Player, Integer> entry : Purge.activePurge.getScores().entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        if (maxEntry == null) {
            return null;
        }
        Player first = maxEntry.getKey();
        Player second;

        if (place == 1) {
            return first;
        } else if (place == 2) {
            for (Map.Entry<Player, Integer> entry : Purge.activePurge.getScores().entrySet()) {
                if (entry.getValue().compareTo(maxEntry.getValue()) > 0 && !entry.getKey().equals(first)) {
                    maxEntry = entry;
                }
            }
            second = maxEntry.getKey();
            return second;
        } else if (place == 3) {
            for (Map.Entry<Player, Integer> entry : Purge.activePurge.getScores().entrySet()) {
                if (entry.getValue().compareTo(maxEntry.getValue()) > 0 && !entry.getKey().equals(first)) {
                    maxEntry = entry;
                }
            }
            second = maxEntry.getKey();

            for (Map.Entry<Player, Integer> entry : Purge.activePurge.getScores().entrySet()) {
                if (entry.getValue().compareTo(maxEntry.getValue()) > 0 && !entry.getKey().equals(first) && !entry.getKey().equals(second)) {
                    maxEntry = entry;
                }
            }

            return maxEntry.getKey();
        }
        return null;
    }
}
