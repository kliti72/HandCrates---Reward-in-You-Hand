package com.pvpshield.customitemreward;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.pvpshield.customitemreward.config.ConfigAnimatedCrates;
import com.pvpshield.customitemreward.config.ConfigManager;
import com.pvpshield.customitemreward.event.UseItemEvent;

import javax.annotation.Nonnull;

public class CustomItemReward extends JavaPlugin {

    ConfigManager configManager;
    ConfigAnimatedCrates configAnimatedCrates;

    public CustomItemReward(@Nonnull JavaPluginInit init) {
        super(init);
        this.configManager = new ConfigManager();
        this.configAnimatedCrates = this.configManager.getConfig();

    }

    protected void start() {}

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new CoreCommand(this));
        UseItemEvent useItemEvent = new UseItemEvent(this);
        useItemEvent.register();
    }

    public void reload() {
        this.configManager.reload();
        this.configAnimatedCrates = this.configManager.getConfig();
    }

    @Override
    protected void shutdown() {
        System.out.println("Saving config...");
        this.configManager.load();
        this.configManager.reload();
        this.configManager.save();
        System.out.println("Config saved at: " + configManager.getConfigFIle().getAbsolutePath());
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    public ConfigAnimatedCrates getPluginConfig() {
        return this.configAnimatedCrates;
    }

}