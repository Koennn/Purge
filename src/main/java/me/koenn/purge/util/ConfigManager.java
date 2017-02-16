package me.koenn.purge.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

/**
 * Copyright (C) Koenn - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class ConfigManager {

    private final FileConfiguration config;
    private final Plugin plugin;

    /**
     * Setup the variables.
     *
     * @param plugin Plugin object instance
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ConfigManager(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        //Set variables.
        this.plugin = plugin;
        this.config = plugin.getConfig();

        //Make the config.yml file if it doesn't exist.
        if (!(new File(plugin.getDataFolder(), "config.yml")).exists()) {
            plugin.saveDefaultConfig();
        }
    }

    /**
     * Set a String with a certain key on a certain path.
     *
     * @param value Value to set
     * @param key   Key to set
     * @param path  Path to set it at
     */
    public void setString(String value, String key, String... path) {
        //Create the section if it doesn't exist and set the value.
        createSection(path).set(key, value);

        //Save the config.
        plugin.saveConfig();
    }

    /**
     * Get a String from a path using a key.
     *
     * @param key  Key to get the String
     * @param path Path to get the String from
     * @return String value
     */
    public String getString(String key, String... path) {
        return translateAlternateColorCodes('&', getSection(path).get(key).toString());
    }

    /**
     * Set a List<String> with a certain key on a certain path.
     *
     * @param list List to set
     * @param key  Key to set
     * @param path Path to set it at
     */
    public void setList(List<String> list, String key, String... path) {
        //Create the section if it doesn't exist and set the value.
        createSection(path).set(key, list);

        //Save the config.
        plugin.saveConfig();
    }

    /**
     * Get a List<String> from a path using a key.
     *
     * @param key  Key to get the List
     * @param path Path to get the List from
     * @return List<String> value
     */
    public List<String> getList(String key, String... path) {
        return getSection(path).getList(key).stream().map(o -> translateAlternateColorCodes('&', o.toString())).collect(Collectors.toList());
    }

    /**
     * Create a ConfigurationSection at a certain path.
     *
     * @param path Path to create the section at
     * @return ConfigurationSection object instance
     */
    private ConfigurationSection createSection(String... path) {
        //Make the ConfigurationSection variable.
        ConfigurationSection section = null;

        //Loop through the path.
        for (String p : path) {

            //Check if the section is not null.
            if (section != null) {

                //Check if the current path exists.
                if (section.getConfigurationSection(p) == null) {

                    //Create the section with the path.
                    section = section.createSection(p);
                } else {

                    //Set the section to the current path.
                    section = section.getConfigurationSection(p);
                }
            } else {

                //Check if the section exists.
                if (config.getConfigurationSection(p) == null) {

                    //Create the section.
                    section = config.createSection(p);
                } else {

                    //Get the section.
                    section = config.getConfigurationSection(p);
                }
            }
        }

        //Check if the section is null.
        if (section == null) {

            //Throw an exception.
            throw new IllegalArgumentException("Section cannot be null");
        }
        return section;
    }

    /**
     * Get a ConfigurationSection at a certain path.
     *
     * @param path Path to get the section from
     * @return ConfigurationSection object instance
     */
    private ConfigurationSection getSection(String... path) {
        ConfigurationSection section = null;
        for (String p : path) {
            if (section != null) {
                section = section.getConfigurationSection(p);
            } else {
                section = config.getConfigurationSection(p);
            }
        }
        if (section == null) {
            throw new IllegalArgumentException("Section cannot be null");
        }
        return section;
    }

    /**
     * Get the FileConfiguration object from this plugin.
     *
     * @return FileConfiguration object instance
     */
    public FileConfiguration getConfiguration() {
        return config;
    }
}

