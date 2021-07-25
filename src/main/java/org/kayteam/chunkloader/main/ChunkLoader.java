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
import org.kayteam.chunkloader.util.Color;
import org.kayteam.chunkloader.util.Metrics;
import org.kayteam.chunkloader.util.UpdateChecker;
import org.kayteam.chunkloader.util.YML;

import java.util.HashMap;
import java.util.Map;

public class ChunkLoader extends JavaPlugin {

    public static ChunkLoader chunkLoader;
    public ChunkManager chunkManager;

    public YML messages;
    public YML messages_es = new YML(this, "messages_es");
    public YML messages_en = new YML(this, "messages_en");
    public YML data = new YML(this,"data");
    public YML config = new YML(this,"config");

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
        checkPaper();
        registerFiles();
        loadConfig();
        loadMessages();
        registerCommands();
        enableBStats();
        enablePluginUpdateChecker();
        registerListeners();
        loadAll();
        getLogger().info(Color.convert(logPrefix+"The plugin was enabled successfully."));
        getLogger().info(Color.convert(logPrefix+"Developed by segu23#4485."));
    }

    private void loadConfig(){
        chunkManager.chunkLoad = config.getFile().getBoolean("chunk-load", true);
        lang = config.getFile().getString("lang", "en");
        prefix = config.getFile().getString("plugin-prefix", "&2&lChunk&a&lLoader &8&l> ");
        chunkManager.loadChunkList();
    }

    public ChunkManager getChunkManager(){
        return chunkManager;
    }

    private void loadMessages(){
        try{
            messages = new YML(this,"messages_"+ lang);
            getLogger().info(Color.convert(logPrefix+messages.getFile().getString("logs.messages").replaceAll("%lang%", "messages_"+lang)));
        }catch (Exception e){
            getLogger().info(Color.convert(logPrefix+messages.getFile().getString("logs.messages-error").replaceAll("%lang%", "messages_"+lang)));
        }
    }

    private void registerFiles(){
        config.registerFile();
        messages_es.registerFile();
        messages_en.registerFile();
        data.registerFile();
    }

    private void enablePluginUpdateChecker(){
        new UpdateChecker(this, 92834).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLogger().info(Color.convert(logPrefix+"&fThere is not a new update available."));
                newUpdate = false;
            } else {
                getLogger().info(Color.convert(logPrefix+"&aThere is a new update available."));
                newUpdate = true;
            }
        });
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
        if(config.getFile().getBoolean("chunk-load")){
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
        getLogger().info(Color.convert(logPrefix+"The plugin was successfully disabled."));
        getLogger().info(Color.convert(logPrefix+"Developed by segu23#4485."));
    }
}

