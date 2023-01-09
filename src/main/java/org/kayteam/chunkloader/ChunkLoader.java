package org.kayteam.chunkloader;

import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.chunkloader.chunk.ChunkManager;
import org.kayteam.chunkloader.commands.*;
import org.kayteam.chunkloader.listeners.ChunkUnloadListener;
import org.kayteam.chunkloader.listeners.PlayerJoinListener;
import org.kayteam.chunkloader.util.Metrics;
import org.kayteam.chunkloader.util.UpdateChecker;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.storageapi.storage.YML;
import org.kayteam.storageapi.storage.Yaml;
import org.kayteam.storageapi.utils.BrandSender;

import java.util.Objects;

public class ChunkLoader extends JavaPlugin {

    private static ChunkLoader instance;

    private static ChunkManager chunkManager;
    public static YML messages;
    private static YML messages_es;
    private static YML messages_en;
    private static YML messages_ru;
    private static YML messages_fr;
    public static YML data;
    public static YML config;
    public static final String logPrefix = "&2Chunk&aLoader &7>> &f";
    private static String lang;

    @Override
    public void onEnable() {
        initializeInstance(this);
        enablePluginUpdateChecker();
        checkPaper();
        registerFiles();
        loadConfig();
        loadMessages();
        registerCommands();
        enableBStats();
        registerListeners();
        loadAll();
        inventoryManager.registerManager();
        BrandSender.onEnable(this);
    }

    private static void initializeInstance(ChunkLoader plugin) {
        instance = plugin;
        chunkManager = new ChunkManager();
        inventoryManager = new InventoryManager(plugin);
    }

    private static void loadConfig() {
        if (config.contains("chunk-load")) {
            chunkManager.chunkLoad = config.getBoolean("chunk-load", true);
        } else {
            config.set("chunk-load", true);
            config.save();
        }
        if (config.contains("log-chunk-load")) {
            chunkManager.chunkLoadLogs = config.getBoolean("log-chunk-load", true);
        } else {
            config.set("log-chunk-load", true);
            config.save();
        }
        if (config.contains("updateChecker")) {
            chunkManager.updateChecker = config.getBoolean("update-checker", true);
        } else {
            config.set("update-checker", true);
            config.save();
        }
        if (config.contains("lang")) {
            lang = config.getString("lang", "en");
        } else {
            config.set("lang", "en");
            config.save();
        }
    }

    public static ChunkManager getChunkManager() {
        return chunkManager;
    }

    private static void loadMessages() {
        try {
            messages = new YML(instance, "messages_" + lang);
            messages.register();
            Yaml.sendSimpleMessage(instance.getServer().getConsoleSender(), messages_en.getString("logs.messages"), new String[][]{{"%lang%", "messages_" + lang}});
        } catch (Exception e) {
            Yaml.sendSimpleMessage(instance.getServer().getConsoleSender(), messages_en.getString("logs.messages-error"), new String[][]{{"%lang%", "messages_" + lang}});
        }
    }

    private static void registerFiles() {
        messages_es = new YML(instance, "messages_es");
        messages_es.register();
        messages_en = new YML(instance, "messages_en");
        messages_en.register();
        messages_ru = new YML(instance, "messages_ru");
        messages_ru.register();
        messages_fr = new YML(instance, "messages_fr");
        messages_fr.register();
        config = new YML(instance, "config");
        config.register();
        data = new YML(instance, "data");
        data.register();
    }

    private static void enablePluginUpdateChecker() {
        updateChecker = new UpdateChecker(instance, 92834);
        if (updateChecker.getUpdateCheckResult().equals(UpdateChecker.UpdateCheckResult.OUT_DATED)) {
            updateChecker.sendOutDatedMessage(instance.getServer().getConsoleSender());
        }
    }

    private static UpdateChecker updateChecker;

    public static UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    private static InventoryManager inventoryManager;

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    private void enableBStats() {
        int pluginId = 12091;
        new Metrics(this, pluginId);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChunkUnloadListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    private void loadAll() {
        if (chunkManager.isChunkLoad()) {
            chunkManager.enableChunkLoad();
        }
    }

    private void checkPaper() {
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            getChunkManager().setPaperState(true);
        } catch (ClassNotFoundException ignored) {
            getChunkManager().setPaperState(false);
        }
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("addchunk")).setExecutor(new Command_AddChunk());
        if (chunkManager.isWorldEdit()) {
            Objects.requireNonNull(getCommand("addchunkregion")).setExecutor(new Command_AddChunkRegion());
            Objects.requireNonNull(getCommand("removechunkregion")).setExecutor(new Command_RemoveChunkRegion());
        }
        Objects.requireNonNull(getCommand("removechunk")).setExecutor(new Command_RemoveChunk());
        Objects.requireNonNull(getCommand("chunkloader")).setExecutor(new Command_ChunkLoader());
    }

    public static ChunkLoader getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        BrandSender.onDisable(this);
    }
}

