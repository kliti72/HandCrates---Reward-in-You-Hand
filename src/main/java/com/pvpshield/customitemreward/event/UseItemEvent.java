package com.pvpshield.customitemreward.event;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChain;
import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChains;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketFilter;
import com.hypixel.hytale.server.core.task.TaskRegistry;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.pvpshield.customitemreward.CustomItemReward;
import com.pvpshield.customitemreward.config.ConfigAnimatedCrates;
import com.pvpshield.customitemreward.config.model.Crates;
import com.pvpshield.customitemreward.config.model.ItemReward;
import com.pvpshield.customitemreward.ui.UIFlowerAnimation;
import com.pvpshield.customitemreward.utils.ResolvePlayer;
import com.pvpshield.customitemreward.utils.TinyMsg;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class UseItemEvent {

    private static final int ANIMATION_TICKS = 20;
    private static final long ANIMATION_INTERVAL_MS = 150;

    private final CustomItemReward plugin;

    public UseItemEvent(CustomItemReward plugin) {
        this.plugin = plugin;
    }

    public void register() {
        PacketAdapters.registerInbound((PlayerPacketFilter) (playerRef, packet) -> {
            if (!(packet instanceof SyncInteractionChains syncPacket)) return false;

            for (SyncInteractionChain chain : syncPacket.updates) {
                if (chain.interactionType != InteractionType.Secondary) continue;

                World world = Universe.get().getWorld(playerRef.getWorldUuid());
                world.execute(() -> handleInteraction(playerRef, world, chain));
            }

            return false;
        });
    }

    // --- Interaction handling ---

    private void handleInteraction(PlayerRef playerRef, World world, SyncInteractionChain chain) {
        String cratesName = getCratesNameFromHand(playerRef, world, chain);
        if (cratesName == null) return;

        ConfigAnimatedCrates config = plugin.getPluginConfig();
        Crates crates = config.getCratesByName(cratesName);
        if (crates == null) return;

        giveCratesReward(world, crates, playerRef, config, chain.equipSlot);
    }

    private void giveCratesReward(World world, Crates crates, PlayerRef playerRef, ConfigAnimatedCrates config, int slot) {
        List<ItemReward> rewards = crates.getItemRewards();

        if (rewards.isEmpty()) {
            playerRef.sendMessage(TinyMsg.parse("<red>This crates has no rewards. Set them with /holdcrates --edit=" + crates.getName()));
            return;
        }

        ItemContainer hotbar = getHotbar(playerRef, world);
        if (hotbar == null) return;

        DefaultAssetMap<String, Item> assetMap = Item.getAssetMap();


        List<ItemStack> itemStacks = rewards.stream()
                .map(r -> new ItemStack(r.getId(), r.getQuantity()))
                .collect(Collectors.toList());

        if (!hotbar.canAddItemStacks(itemStacks)) {
            playerRef.sendMessage(TinyMsg.parse(config.inventory_full));
            return;
        }

        hotbar.removeItemStackFromSlot((short) slot, 1);
        sendRewardWithAnimation(world, playerRef, itemStacks);
        playerRef.sendMessage(TinyMsg.parse(config.recive.replace("{crates}", crates.getDisplayName())));
    }

    // --- Inventory helpers ---

    private String getCratesNameFromHand(PlayerRef playerRef, World world, SyncInteractionChain chain) {
        ItemContainer hotbar = getHotbar(playerRef, world);
        if (hotbar == null) return null;

        ItemStack item = hotbar.getItemStack((short) chain.equipSlot);
        if (item == null) return null;

        return item.getFromMetadataOrNull("hand-crates", Codec.STRING);
    }

    private ItemContainer getHotbar(PlayerRef playerRef, World world) {
        EntityStore entityStore = world.getEntityStore();
        Store<EntityStore> store = entityStore.getStore();

        InventoryComponent component = store.getComponent(
                Objects.requireNonNull(playerRef.getReference()),
                InventoryComponent.Hotbar.getComponentType()
        );

        return component != null ? component.getInventory() : null;
    }

    // --- Animation ---

    private void sendRewardWithAnimation(World world, PlayerRef playerRef, List<ItemStack> itemStacks) {
        Player player = ResolvePlayer.getPlayerFromPlayerRef(playerRef);
        TaskRegistry taskRegistry = plugin.getTaskRegistry();

        AtomicInteger tick = new AtomicInteger(0);
        AtomicReference<ScheduledFuture<?>> animationRef = new AtomicReference<>();

        UIFlowerAnimation animation = new UIFlowerAnimation(playerRef, "example");
        player.getHudManager().addCustomHud(playerRef, animation);

        ScheduledFuture<?> future = (ScheduledFuture<Void>) HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
            int currentTick = tick.incrementAndGet();

            world.execute(() -> {
                animation.updateAnimation(currentTick);

                if (currentTick >= ANIMATION_TICKS) {
                    sendRewardPlayer(itemStacks, player);
                    player.getHudManager().removeCustomHud(playerRef, "example");
                }
            });

            if (currentTick >= ANIMATION_TICKS) {
                animationRef.get().cancel(false);
            }

        }, 0, ANIMATION_INTERVAL_MS, TimeUnit.MILLISECONDS);

        animationRef.set(future);
        taskRegistry.registerTask((ScheduledFuture<Void>) future);
    }


    public void sendRewardPlayer(List<ItemStack> itemStacks, Player player) {
        DefaultAssetMap<String, Item> itemMap = Item.getAssetMap();
        for (ItemStack item : itemStacks)
            Player.giveItem(item,
                    player.getReference(),
                    player.getReference().getStore());
    }

}