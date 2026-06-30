package com.pvpshield.customitemreward.config.model;

import java.util.List;

public class Crates {

    String name;
    String displayName;
    String lore;
    String type;
    List<ItemReward> itemRewards;

    public Crates(String name, String displayName, String lore, String type, List<ItemReward> itemRewards) {
        this.name = name;
        this.lore = lore;
        this.displayName = displayName;
        this.type = type;
        this.itemRewards = itemRewards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ItemReward> getItemRewards() {
        return itemRewards;
    }

    public void setItemRewards(List<ItemReward> itemRewards) {
        this.itemRewards = itemRewards;
    }

    public String getDisplayName() {
        return displayName != null ? displayName : "";
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return this.name;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }
}