package com.pvpshield.customitemreward;

import javax.annotation.Nonnull;

import com.hypixel.hytale.assetstore.AssetPack;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.ISystem;
import com.hypixel.hytale.protocol.*;
import com.hypixel.hytale.protocol.ItemAppearanceCondition;
import com.hypixel.hytale.protocol.packets.assets.UpdateItems;
import com.hypixel.hytale.protocol.packets.interface_.Page;
import com.hypixel.hytale.server.core.asset.common.PlayerCommonAssets;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.item.config.*;
import com.hypixel.hytale.server.core.asset.type.item.config.metadata.ItemDisplayMetadata;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.windows.ContainerWindow;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemQuality;
import com.hypixel.hytale.server.core.inventory.ItemContext;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketFilter;
import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
import com.hypixel.hytale.server.core.modules.item.ItemModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.pvpshield.customitemreward.config.ConfigAnimatedCrates;
import com.pvpshield.customitemreward.config.ConfigManager;
import com.pvpshield.customitemreward.config.model.Crates;
import com.pvpshield.customitemreward.config.model.ItemReward;
import com.pvpshield.customitemreward.utils.ResolvePlayer;
import com.pvpshield.customitemreward.utils.TinyMsg;
import org.bson.BsonDocument;
import org.bson.BsonString;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class CoreCommand extends AbstractPlayerCommand {

    private static final short REWARD_CONTAINER_SIZE = 50;

    private final CustomItemReward plugin;

    private final OptionalArg<Boolean> reload;
    private final OptionalArg<String> create;
    private final OptionalArg<String> display;
    private final OptionalArg<String> edit;
    private final OptionalArg<BlockType> type;
    private final OptionalArg<String> lore;
    private final OptionalArg<Boolean> list;
    private final OptionalArg<String> delete;
    private final OptionalArg<String> give;
    private final OptionalArg<PlayerRef> player;

    public CoreCommand(CustomItemReward plugin) {
        super("handcrates", "Manage Hand Crates");
        this.plugin = plugin;
        this.reload  = withOptionalArg("reload",  "Reload the plugin",             ArgTypes.BOOLEAN);
        this.create  = withOptionalArg("create",  "Name of the crate to create",   ArgTypes.STRING);
        this.display = withOptionalArg("display", "Display name for a crate",      ArgTypes.STRING);
        this.edit    = withOptionalArg("edit",    "Name of the crate to edit",      ArgTypes.STRING);
        this.type    = withOptionalArg("type",    "Block type for the crate",       ArgTypes.BLOCK_TYPE_ASSET);
        this.list    = withOptionalArg("list",    "List all crates",                ArgTypes.BOOLEAN);
        this.lore    = withOptionalArg("lore",    "Set lore of crates",                ArgTypes.STRING);
        this.delete  = withOptionalArg("delete",  "Name of the crate to delete",   ArgTypes.STRING);
        this.give    = withOptionalArg("give",    "Name of the crate to give",      ArgTypes.STRING);
        this.player  = withOptionalArg("player",  "Target player",                 ArgTypes.PLAYER_REF);
    }

    @Override
    protected void execute(
            @Nonnull CommandContext ctx,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef sender,
            @Nonnull World world
    ) {

        if(!sender.hasPermission("handcrates.admin")) {
                sendError(sender, "Not have permission handcrates.admin");
        }

        if (reload.provided(ctx)) {
            handleReload(ctx, sender);
            return;
        }

        if (give.provided(ctx) && player.provided(ctx)) {
            handleGive(ctx, sender);
            return;
        }

        if (delete.provided(ctx)) {
            handleDelete(ctx, sender);
            return;
        }

        if (edit.provided(ctx) && lore.provided(ctx)) {
            handleLore(ctx, store, ref, sender, world);
            return;
        }

        if (edit.provided(ctx)) {
            handleEdit(ctx, store, ref, sender, world);
            return;
        }

        if (display.provided(ctx) && create.provided(ctx)) {
            handleDisplayName(ctx, sender);
            return;
        }

        if (list.provided(ctx) && Boolean.TRUE.equals(list.get(ctx))) {
            handleList(sender);
            return;
        }

        if (create.provided(ctx) && type.provided(ctx)) {
            handleCreate(ctx, sender);
            return;
        }

        printHelp(sender);
    }

    // -------------------------------------------------------------------------
    // Sub-handlers
    // -------------------------------------------------------------------------

    private void handleReload(CommandContext ctx, PlayerRef sender) {
        if (!Boolean.TRUE.equals(reload.get(ctx))) {
            sendError(sender, "Use --reload=true to reload the plugin.");
            return;
        }
        plugin.reload();
        sendMessage(sender, "Plugin reloaded successfully.");
    }

    private void handleGive(CommandContext ctx, PlayerRef sender) {
        String crateName = give.get(ctx);
        ConfigAnimatedCrates config = plugin.getPluginConfig();

        Crates crates = config.getCratesByName(crateName);
        if (crates == null) {
            sendError(sender, "Crate \"" + crateName + "\" not found. Use --list=true to see all crates.");
            return;
        }

        PlayerRef target = player.get(ctx);
        if (target == null) {
            sendError(sender, "Player not found.");
            return;
        }

        ItemStack item = buildCrateItem(crates);
        Player.giveItem(item, target.getReference(), target.getReference().getStore());

        sendMessage(sender, "Gave crate \"" + crateName + "\" to " + target.getUsername() + ".");
        target.sendMessage(TinyMsg.parse(config.give.replace("{crates}", crateName)));
    }

    private void handleDelete(CommandContext ctx, PlayerRef sender) {
        String crateName = delete.get(ctx);
        if (crateName == null || crateName.isBlank()) {
            sendError(sender, "Please specify a crate name with --delete={name}.");
            return;
        }

        ConfigAnimatedCrates config = plugin.getPluginConfig();
        if (config.deleteCrates(crateName)) {
            plugin.getConfigManager().save();
            sendMessage(sender, "Crate \"" + crateName + "\" deleted.");
        } else {
            sendError(sender, "Crate \"" + crateName + "\" not found. Use --list=true to see all crates.");
        }
    }

    private void handleEdit(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref, PlayerRef sender, World world) {
        String crateName = edit.get(ctx);
        ConfigAnimatedCrates config = plugin.getPluginConfig();

        if (!config.isExistKeys(crateName)) {
            sendError(sender, "Crate \"" + crateName + "\" not found.");
            return;
        }

        openRewardEditor(store, ref, sender, crateName);
    }

    private void handleLore(CommandContext ctx, Store<EntityStore> store, Ref<EntityStore> ref, PlayerRef sender, World world) {
        String crateName = edit.get(ctx);
        String loreC = lore.get(ctx);
        ConfigManager configManager = plugin.getConfigManager();
        ConfigAnimatedCrates config = plugin.getPluginConfig();

        if (!config.isExistKeys(crateName)) {
            sendError(sender, "Crate \"" + crateName + "\" not found.");
            return;
        }

        sendMessage(sender, "Lore is updated.");
        config.updateLore(crateName, loreC);
        configManager.save();
    }

    private void handleDisplayName(CommandContext ctx, PlayerRef sender) {
        String crateName    = create.get(ctx);
        String displayName  = display.get(ctx);
        ConfigAnimatedCrates config = plugin.getPluginConfig();

        if (config.getCratesByName(crateName) == null) {
            sendError(sender, "Crate \"" + crateName + "\" not found.");
            return;
        }

        config.updateDisplayName(crateName, displayName);
        plugin.getConfigManager().save();
        sendMessage(sender, "Display name of \"" + crateName + "\" updated.");
    }

    private void handleList(PlayerRef sender) {
        ArrayList<Crates> crates = plugin.getPluginConfig().getCrates();

        if (crates.isEmpty()) {
            sendMessage(sender, "No crates created yet.");
            return;
        }

        sender.sendMessage(TinyMsg.parse("<green>=== Hand Crates List ===</green>"));
        crates.forEach(c ->
                sender.sendMessage(TinyMsg.parse("<green> - " + c.getName() + "</green> " + c.getDisplayName()))
        );
    }

    private void handleCreate(CommandContext ctx, PlayerRef sender) {
        String crateName = create.get(ctx);
        BlockType blockType = type.get(ctx);

        if (!(blockType instanceof BlockType)) {
            sendError(sender, "Invalid block type.");
            return;
        }

        String defaultDisplay = "<green><bold>" + crateName + "</bold></green> <white><italic>(right click)</italic></white>";
        Crates crates = new Crates(crateName, defaultDisplay, "Default lore", blockType.getId(), new ArrayList<>());

        ConfigAnimatedCrates config = plugin.getPluginConfig();
        if (config.createCrates(crates)) {
            plugin.getConfigManager().save();
            sendMessage(sender, "Crate \"" + crateName + "\" created with block type " + blockType.getId() + ".");
        } else {
            sendError(sender, "A crate named \"" + crateName + "\" already exists.");
        }
    }

    // -------------------------------------------------------------------------
    // Reward editor
    // -------------------------------------------------------------------------

    private void openRewardEditor(Store<EntityStore> store, Ref<EntityStore> ref, PlayerRef senderRef, String crateName) {
        Player player = ResolvePlayer.getPlayerFromPlayerRef(senderRef);
        SimpleItemContainer rewardContainer = new SimpleItemContainer(REWARD_CONTAINER_SIZE);

        // Pre-fill with existing rewards
        ConfigAnimatedCrates config = plugin.getPluginConfig();
        Crates crates = config.getCratesByName(crateName);
        if (crates != null && crates.getItemRewards() != null) {
            for (ItemReward reward : crates.getItemRewards()) {
                rewardContainer.addItemStack(new ItemStack(reward.getId(), reward.getQuantity()));
            }
        }

        ContainerWindow window = new ContainerWindow(rewardContainer);
        player.getPageManager().setPageWithWindows(ref, store, Page.Inventory, true, window);

        window.registerCloseEvent(event -> saveRewardsFromContainer(rewardContainer, crateName));
    }

    private void saveRewardsFromContainer(SimpleItemContainer container, String crateName) {
        ConfigAnimatedCrates config = plugin.getPluginConfig();

        if (config.getCratesByName(crateName) == null) return;

        List<ItemReward> rewards = new ArrayList<>();
        container.forEach((slot, item) -> {


            if (item != null) {
                rewards.add(new ItemReward(item.getItemId(), item.getQuantity()));
            }
        });

        config.updateReward(crateName, rewards);
        plugin.getConfigManager().save();
    }

    // -------------------------------------------------------------------------
    // Item builder
    // -------------------------------------------------------------------------

    private ItemStack buildCrateItem(Crates crates) {
        BsonDocument metadata = new BsonDocument();
        metadata.put("hand-crates", new BsonString(crates.getName()));

        ItemDisplayMetadata displayMeta = new ItemDisplayMetadata(
                TinyMsg.parse(crates.getDisplayName()),
                TinyMsg.parse(crates.getLore())
        );

        ItemStack item = new ItemStack(crates.getType(), 1, metadata)
                .withMetadata(ItemDisplayMetadata.KEYED_CODEC, displayMeta);

        return item;
    }

    // -------------------------------------------------------------------------
    // Messaging helpers
    // -------------------------------------------------------------------------

    private void sendError(PlayerRef recipient, String message) {
        recipient.sendMessage(TinyMsg.parse("<red>[Hand Crates] " + message + "</red>"));
    }

    private void sendMessage(PlayerRef recipient, String message) {
        recipient.sendMessage(TinyMsg.parse("<green>[Hand Crates]</green> <white>" + message + "</white>"));
    }

    private static void printHelp(PlayerRef recipient) {
        recipient.sendMessage(TinyMsg.parse("<green><bold>Hand Crates — Commands</bold></green>"));
        printHelpLine(recipient, "--create={name} --type={type}",              "Create a new crate");
        printHelpLine(recipient, "--edit={name}",                              "Edit the rewards for a crate");
        printHelpLine(recipient, "--edit={name} --lore=\"lore\"",              "Create a new crate");
        printHelpLine(recipient, "--list=true",                                "List all crates");
        printHelpLine(recipient, "--give={crate} --player={player}",           "Give a crate to a player");
        printHelpLine(recipient, "--create={name} --display={display_name}",   "Set display name (supports color tags)");
        printHelpLine(recipient, "--delete={name}",                            "Delete a crate");
        printHelpLine(recipient, "--reload=true",                              "Reload the plugin");
    }

    private static void printHelpLine(PlayerRef recipient, String usage, String description) {
        recipient.sendMessage(TinyMsg.parse(
                "<green>/handcrates " + usage + "</green> <gray>— " + description + "</gray>"
        ));
    }
}