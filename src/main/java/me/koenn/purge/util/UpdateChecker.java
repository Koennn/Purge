package me.koenn.purge.util;

import me.koenn.purge.Purge;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * <p>
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Koen Willemse, February 2017
 */
public class UpdateChecker {

    public static boolean updateAvailable() {
        try {
            JSONObject json = readJsonFromUrl(Purge.getReferences().VERSION_URL);
            String latest = json.getString("latest");
            return !latest.equals(Purge.getInstance().getDescription().getVersion());
        } catch (IOException e) {
            Purge.getInstance().getLogger().warning("Error in UpdateChecker. Are you connected to the internet?");
            throw new RuntimeException("Unable to get latest version. Are you connected to the internet?");
        }
    }

    public static void sendUpdateMessage(Player player) {
        FancyMessage message = new FancyMessage()
                .text("An update is available for Purge. ")
                .color(ChatColor.GREEN)
                .then("Download here!")
                .link("https://github.com/Koennn/Purge")
                .tooltip("Click to open download page!")
                .color(ChatColor.YELLOW)
                .style(ChatColor.BOLD);
        message.send(player);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}
