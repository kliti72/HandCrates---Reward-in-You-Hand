package com.pvpshield.customitemreward.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();

    private ConfigAnimatedCrates config;
    private final File configFile;

    public ConfigManager() {
        File folder = new File("mods/animatedCrates");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        configFile = new File(folder, "config.json");
        System.out.println("Config path: " + configFile.getAbsolutePath());
        load();
    }

    public void load() {
        try {
            if (!configFile.exists()) {
                config = new ConfigAnimatedCrates();
                save();
                return;
            }

            ConfigAnimatedCrates loaded = GSON.fromJson(new FileReader(configFile), ConfigAnimatedCrates.class);
            config = (loaded != null) ? loaded : new ConfigAnimatedCrates();

        } catch (Exception e) {
            e.printStackTrace();
            config = new ConfigAnimatedCrates();
        }
    }

    public void save() {
        try {
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }

            try (Writer writer = new FileWriter(configFile)) {
                GSON.toJson(config, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConfigAnimatedCrates getConfig() {
        return config;
    }

    public File getConfigFIle() {
        return this.configFile;
    }

    public void reload() {
        try (Reader reader = new FileReader(configFile)) {
            ConfigAnimatedCrates newConfig = GSON.fromJson(reader, ConfigAnimatedCrates.class);
            if (newConfig != null) {
                this.config = newConfig;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}