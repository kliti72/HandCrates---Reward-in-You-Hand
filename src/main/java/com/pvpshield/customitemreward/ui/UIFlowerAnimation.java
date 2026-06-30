package com.pvpshield.customitemreward.ui;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;
import java.util.*;

public class UIFlowerAnimation extends CustomUIHud {
    UICommandBuilder uiCommandBuilder;

    List<String> listDiItem = Arrays.asList(
            "Deco_Trophy_Harvest",
            "Fish_Eel_Moray_Item",
            "Fish_Piranha_Item",
            "Fish_Jellyfish_Green_Item",
            "Fish_Jellyfish_Cyan_Item",
            "Ingredient_Voidheart",
            "Weapon_Shield_Mithril",
            "Weapon_Shortbow_Mithril",
            "Weapon_Shield_Adamantite",
            "Plant_Flower_Common_Pink2",
            "Wood_Amber_Trunk_Half",
            "Plant_Fruit_Apple",
            "Potion_Antidote",
            "Plant_Seeds_Mana1",
            "Plant_Leaves_Beech",
            "Deco_Trophy_Harvest",
            "Fish_Eel_Moray_Item",
            "Fish_Piranha_Item",
            "Fish_Jellyfish_Green_Item",
            "Fish_Jellyfish_Cyan_Item",
            "Ingredient_Voidheart",
            "Weapon_Shield_Mithril",
            "Weapon_Shortbow_Mithril",
            "Weapon_Shield_Adamantite",
            "Plant_Flower_Common_Pink2",
            "Wood_Amber_Trunk_Half",
            "Plant_Fruit_Apple",
            "Potion_Antidote",
            "Plant_Seeds_Mana1",
            "Plant_Leaves_Beech"
    );

    public UIFlowerAnimation(@Nonnull PlayerRef playerRef, @NonNullDecl String key) {
        super(playerRef, key);
    }

    @Override
    public void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        this.uiCommandBuilder = uiCommandBuilder;
        uiCommandBuilder.append("CascadeAnimation.ui");
    }

    public void updateAnimation(int tick) {
        this.flowerAnimation(tick);
        update(false, this.uiCommandBuilder);
    }

    public void randomItemPage() {
        uiCommandBuilder.clear("#container");

        Random rand = new Random();

            for (int i = 0; i < 30; i++)
                {
                    int topSpace = i % 30 * 64;
                    uiCommandBuilder.appendInline(
                            "#container",
                            "Group #Row" + i + " {Anchor: (Left: 0, Top: {top}, Width: 1920, Height: 64); LayoutMode: Left; Padding: 20; }"
                                    .replace("{top}", String.valueOf(topSpace)));
                }

        for (int i = 0; i < 30; i++) {
            for (int a = 0; a < 30; a++) {
                String itemRow = "ItemIcon #{selector} { Anchor: (Width: 64, Height: 64); }"
                        .replace("{selector}", "item" + String.format("%02d", i) + String.format("%02d", a));
                uiCommandBuilder.appendInline("#Row" + i, itemRow);
                if (rand.nextInt(100) < 35) {
                    uiCommandBuilder.set("#item" + String.format("%02d", i) + String.format("%02d", a) + ".ItemId",
                            listDiItem.get(rand.nextInt(30)));
                }
            }
        };
    }


    public void flowerAnimation(int tick) {
        uiCommandBuilder.clear("#container");

        int centerX = 960;
        int centerY = 540;
        int petalSize = 64;

        int totalPetals = 8;
        int visiblePetals = Math.min(tick, totalPetals);
        int radius = tick * 10;

        // Petali - usa item reali dalla lista
        String[] petalItems = {
                "Plant_Flower_Common_Pink2",
                "Plant_Leaves_Beech",
                "Plant_Seeds_Mana1",
                "Ingredient_Voidheart",
                "Plant_Flower_Common_Pink2",
                "Plant_Leaves_Beech",
                "Plant_Seeds_Mana1",
                "Ingredient_Voidheart"
        };

        for (int p = 0; p < visiblePetals; p++) {
            double angle = (2 * Math.PI / totalPetals) * p;

            int left = centerX + (int)(Math.cos(angle) * radius) - petalSize / 2;
            int top  = centerY + (int)(Math.sin(angle) * radius) - petalSize / 2;

            String petal = "ItemIcon #petal{p} { Anchor: (Left: {l}, Top: {t}, Width: {w}, Height: {h}); ItemId: \"{id}\"; }"
                    .replace("{p}", String.valueOf(p))
                    .replace("{l}", String.valueOf(left))
                    .replace("{t}", String.valueOf(top))
                    .replace("{w}", String.valueOf(petalSize))
                    .replace("{h}", String.valueOf(petalSize))
                    .replace("{id}", petalItems[p]);

            uiCommandBuilder.appendInline("#container", petal);
        }

        // Centro - usa un item reale
        int centerLeft = centerX - petalSize / 2;
        int centerTop  = centerY - petalSize / 2;

        String core = "ItemIcon #flowerCore { Anchor: (Left: {l}, Top: {t}, Width: {w}, Height: {h}); ItemId: \"{id}\"; }"
                .replace("{l}", String.valueOf(centerLeft))
                .replace("{t}", String.valueOf(centerTop))
                .replace("{w}", String.valueOf(petalSize))
                .replace("{h}", String.valueOf(petalSize))
                .replace("{id}", "Plant_Fruit_Apple");

        uiCommandBuilder.appendInline("#container", core);
    }


}