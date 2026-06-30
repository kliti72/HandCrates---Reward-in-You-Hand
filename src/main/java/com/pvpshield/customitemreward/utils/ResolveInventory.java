package com.pvpshield.customitemreward.utils;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.InventoryUtils;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class ResolveInventory {

    public static void clearInventory(Player player) {

        World world = Universe.get().getWorld(player.getWorld().getName());

        world.execute(() -> {
            Ref<EntityStore> ref = player.getReference();
            Store<EntityStore> store = player.getReference().getStore();

            InventoryUtils.clear(ref, store);
        });

    }

}
