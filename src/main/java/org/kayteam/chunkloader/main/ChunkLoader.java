package org.kayteam.chunkloader.main;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.chunkloader.commands.Command_AddChunk;
import org.kayteam.chunkloader.commands.Command_ChunkLoader;
import org.kayteam.chunkloader.commands.Command_Menu;
import org.kayteam.chunkloader.commands.Command_RemoveChunk;
import org.kayteam.chunkloader.listeners.ChunkUnload;
import org.kayteam.chunkloader.listeners.Inventory_Menu;
import org.kayteam.chunkloader.listeners.OPJoin;
import org.kayteam.chunkloader.menus.Menu;
import org.kayteam.chunkloader.util.*;

import java.util.HashMap;
import java.util.Map;

public class ChunkLoader extends JavaPlugin {

    public static ChunkLoader chunkLoader;
    public ChunkManager chunkManager;

    public Yaml messages;
    public Yaml messages_es = new Yaml(this, "messages_es");
    public Yaml messages_en = new Yaml(this, "messages_en");
    public Yaml data = new Yaml(this,"data");
    public Yaml config = new Yaml(this,"config");

    public String prefix;
    public final String logPrefix = "&2Chunk&aLoader &7>> &f";
    private String lang;
    private Map<Player, Menu> playersInMenu = new HashMap<>();

    public Command_Menu CMD_Menu;

    public boolean newUpdate = false;

    @Override
    public void onEnable() {
        chunkLoader = this;
        chunkManager = new ChunkManager();
        KayTeam.sendBrandMessage(this, "&aEnabled");
        enablePluginUpdateChecker();
        checkPaper();
        registerFiles();
        loadConfig();
        loadMessages();
        registerCommands();
        enableBStats();
        registerListeners();
        loadAll();
    }

    private void loadConfig(){
        chunkManager.chunkLoad = config.getBoolean("chunk-load", true);
        lang = config.getString("lang", "en");
        prefix = config.getString("plugin-prefix", "&2&lChunk&a&lLoader &8&l> ");
        chunkManager.loadChunkList();
    }

    public ChunkManager getChunkManager(){
        return chunkManager;
    }

    private void loadMessages(){
        try{
            messages = new Yaml(this,"messages_"+ lang);
            messages.registerFileConfiguration();
            getLogger().info(Color.convert(logPrefix+messages.getString("logs.messages").replaceAll("%lang%", "messages_"+lang)));
        }catch (Exception e){
            getLogger().info(Color.convert(logPrefix+messages_en.getString("logs.messages-error").replaceAll("%lang%", "messages_"+lang)));
        }
    }

    private void registerFiles(){
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

    private void enableBStats(){
        int pluginId = 	12091;
        Metrics metrics = new Metrics(this, pluginId);
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new OPJoin(), this);
        getServer().getPluginManager().registerEvents(new Inventory_Menu(), this);
    }

    public static ChunkLoader getChunkLoader(){
        return chunkLoader;
    }

    public Map<Player, Menu> getPlayersInMenu(){
        return playersInMenu;
    }

    private void loadAll(){
        getServer().getPluginManager().registerEvents(new ChunkUnload(), this);
        if(config.getBoolean("chunk-load")){
            chunkManager.enableChunkLoad();
        }
        CMD_Menu = new Command_Menu();
    }

    private void checkPaper(){
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            getChunkManager().setPaperState(true);
        } catch (ClassNotFoundException ignored) {
            getChunkManager().setPaperState(false);
        }
    }


    private void registerCommands() {
        getCommand("addchunk").setExecutor(new Command_AddChunk());
        getCommand("removechunk").setExecutor(new Command_RemoveChunk());
        getCommand("chunkloader").setExecutor(new Command_ChunkLoader());
    }

    @Override
    public void onDisable() {
        KayTeam.sendBrandMessage(this, "&cDisabled");
    }
}

