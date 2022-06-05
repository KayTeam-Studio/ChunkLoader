package org.kayteam.chunkloader.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.chunkloader.commands.ComandoYoutube;
import org.kayteam.chunkloader.commands.Command_AddChunk;
import org.kayteam.chunkloader.commands.Command_ChunkLoader;
import org.kayteam.chunkloader.commands.Command_RemoveChunk;
import org.kayteam.chunkloader.listeners.ChunkUnload;
import org.kayteam.chunkloader.listeners.OPJoin;
import org.kayteam.kayteamapi.BrandSender;
import org.kayteam.kayteamapi.bStats.Metrics;
import org.kayteam.kayteamapi.inventory.InventoryManager;
import org.kayteam.kayteamapi.updatechecker.UpdateChecker;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.Objects;

public class MiPlugin extends JavaPlugin {

    private ChunkManager chunkManager;

    public Yaml messages;
    public Yaml messages_es = new Yaml(this, "messages_es");
    public Yaml messages_en = new Yaml(this, "messages_en");
    public Yaml data = new Yaml(this,"data");
    public Yaml config = new Yaml(this,"config");

    public final String logPrefix = "&2Chunk&aLoader &7>> &f";
    private String lang;

    @Override
    public void onEnable() {
        chunkManager = new ChunkManager(this);
        enablePluginUpdateChecker();
        checkPaper();
        registrarArcivos();
        cargarConfig();
        cargarMensajes();
        registrarComandos();
        enableBStats();
        registrarEventos();
        loadAll();
        BrandSender.sendBrandMessage(this, "&aEnabled");
    }

    private void cargarConfig(){
        chunkManager.chunkLoad = config.getBoolean("chunk-load", true);
        chunkManager.chunkLoadLogs = config.getBoolean("log-chunk-load", true);
        lang = config.getString("lang", "en");
    }

    public ChunkManager getChunkManager(){
        return chunkManager;
    }

    private void cargarMensajes(){
        try{
            messages = new Yaml(this,"messages_"+ lang);
            messages.registerFileConfiguration();
            Yaml.sendSimpleMessage(getServer().getConsoleSender(), messages_en.getString("logs.messages"), new String[][]{{"%lang%", "messages_"+lang}});
        }catch (Exception e){
            Yaml.sendSimpleMessage(getServer().getConsoleSender(), messages_en.getString("logs.messages-error"), new String[][]{{"%lang%", "messages_"+lang}});
        }
    }

    private void registrarArcivos(){
        config.registerFileConfiguration();
        messages_es.registerFileConfiguration();
        messages_en.registerFileConfiguration();
        data.registerFileConfiguration();
    }

    private void enablePluginUpdateChecker(){
        updateChecker = new UpdateChecker(this, 92834);
        if (updateChecker.getUpdateCheckResult().equals(UpdateChecker.UpdateCheckResult.OUT_DATED)) {
            updateChecker.sendOutDatedMessage(getServer().getConsoleSender());
        }
    }

    private UpdateChecker updateChecker;
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    private final InventoryManager inventoryManager = new InventoryManager(this);
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    private void enableBStats(){
        int pluginId = 	12091;
        new Metrics(this, pluginId);
    }

    private void registrarEventos(){
        getServer().getPluginManager().registerEvents(new ChunkUnload(this), this);
        getServer().getPluginManager().registerEvents(new OPJoin(this), this);
        getServer().getPluginManager().registerEvents(inventoryManager, this);
    }

    private void loadAll(){
        if(chunkManager.isChunkLoad()){
            chunkManager.enableChunkLoad();
        }
    }

    private void checkPaper(){
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            getChunkManager().setPaperState(true);
        } catch (ClassNotFoundException ignored) {
            getChunkManager().setPaperState(false);
        }
    }


    private void registrarComandos() {
        Objects.requireNonNull(getCommand("addchunk")).setExecutor(new Command_AddChunk(this));
        Objects.requireNonNull(getCommand("removechunk")).setExecutor(new Command_RemoveChunk(this));
        Objects.requireNonNull(getCommand("chunkloader")).setExecutor(new Command_ChunkLoader(this));
        getCommand("youtube").setExecutor(new ComandoYoutube(this));
    }

    @Override
    public void onDisable() {
        BrandSender.sendBrandMessage(this, "&cDisabled");
    }
}

