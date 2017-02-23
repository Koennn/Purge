package me.koenn.purge.util;

import me.koenn.purge.Purge;
import org.bukkit.entity.Player;

import java.util.HashMap;
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
        Map<Player, Integer> scores = new HashMap<>(Purge.activePurge.getScores());

        Map.Entry<Player, Integer> first = null;
        Map.Entry<Player, Integer> second = null;
        Map.Entry<Player, Integer> third = null;

        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            if (first == null || entry.getValue() > first.getValue()) {
                first = entry;
            }
        }

        if (first == null) {
            return null;
        }
        if (place == 1) {
            return first.getKey();
        }

        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            if (second == null) {
                if (entry.getKey() != first.getKey()) {
                    second = entry;
                }
            } else if (entry.getValue() > second.getValue() && entry.getKey() != first.getKey()) {
                second = entry;
            }
        }

        if (second == null) {
            return null;
        }
        if (place == 2) {
            return second.getKey();
        }

        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            if (third == null) {
                if (entry.getKey() != second.getKey() && entry.getKey() != first.getKey()) {
                    third = entry;
                }
            } else if (entry.getValue() > third.getValue() && entry.getKey() != second.getKey() && entry.getKey() != first.getKey()) {
                third = entry;
            }
        }

        if (third == null) {
            return null;
        }
        if (place == 3) {
            return third.getKey();
        }
        return null;
    }
}
