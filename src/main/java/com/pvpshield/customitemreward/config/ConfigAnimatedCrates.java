package com.pvpshield.customitemreward.config;

import com.pvpshield.customitemreward.config.model.Crates;
import com.pvpshield.customitemreward.config.model.ItemReward;

import java.util.ArrayList;
import java.util.List;

public class ConfigAnimatedCrates {

    public String open = "You open the chest {crates} ";
    public String give = "You give crates {crates} ";
    public String recive = "You recive crates {crates} ";
    public String inventory_full = "<red> Your inventory not have amount space. </red>";
    public ArrayList<Crates> cratesArrayList = new ArrayList<>();

    public Boolean createCrates(Crates crates) {
        for (Crates existing : cratesArrayList) {
            if (existing.getName().equals(crates.getName())) {
                return false;
            }
        }
        cratesArrayList.add(crates);
        return true;
    }

    public Boolean removeCrates(Crates crates) {
        return cratesArrayList.removeIf(c -> c.getName().equals(crates.getName()));
    }

    public Boolean updateReward(String cratesName, List<ItemReward> itemRewards) {
        for(Crates c : cratesArrayList) {
            if(c.getName().equals(cratesName)) {
                c.setItemRewards(itemRewards);
                return true;
            }
        }
        return false;
    }

    public Boolean updateLore(String cratesName, String lore) {
        for(Crates c : cratesArrayList) {
            if(c.getName().equals(cratesName)) {
                c.setLore(lore);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Crates> getCrates() {
        return cratesArrayList;
    }

    public Crates getCratesByName(String name) {
        for(Crates crates : this.cratesArrayList) {
            if(crates.getName().equals(name)) {
                return crates;
            }
        }
        return null;
    }

    public Boolean isExistKeys(String name) {
        for(Crates crates : this.cratesArrayList) {
            if(crates.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Boolean updateDisplayName(String cratesName, String displayName) {
        for(Crates crates : this.cratesArrayList) {
            if(crates.getName().equals(cratesName)) {
                crates.setDisplayName(displayName);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteCrates(String cratesName) {
        return this.cratesArrayList.removeIf(c -> c.getName().equals(cratesName));
    }


}