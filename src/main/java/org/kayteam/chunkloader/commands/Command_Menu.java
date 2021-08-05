package org.kayteam.chunkloader.commands;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;
import org.kayteam.chunkloader.menus.Menu;
import org.kayteam.chunkloader.util.Color;

import java.util.List;

public class Command_Menu {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    //GENERAL THINGS
    public ItemStack closeItem = plugin.config.getItemStack("menu.general-options.items.close");
    public ItemStack backItem = plugin.config.getItemStack("menu.general-options.items.back");
    public ItemStack fillItem = plugin.config.getItemStack("menu.general-options.items.fill");
    public ItemStack nextPageItem = plugin.config.getItemStack("menu.general-options.items.next-page");
    public ItemStack previousPageItem = plugin.config.getItemStack("menu.general-options.items.previous-page");

    //MAIN-MENU THINGS
    public String mainMenuTitle = plugin.config.getString("menu.main-menu.title");
    public ItemStack chunkListItem = plugin.config.getItemStack("menu.main-menu.items.chunk-list");
    public ItemStack adminSectionItem = plugin.config.getItemStack("menu.main-menu.items.admin-section");

    //ADMIN-SECTION-MENU THINGS
    public String adminSectionMenuTitle = plugin.config.getString("menu.admin-section.title");

    //CHUNK-LIST-MENU THINGS
    public String chunkListMenuTitle = plugin.config.getString("menu.chunk-list.title");
    public ItemStack chunkListedItem = plugin.config.getItemStack("menu.chunk-list.items.listed");

    //ADMIN-MENU THINGS
    public ItemStack turnOffChunkLoadItem = plugin.config.getItemStack("menu.admin-section.items.turn-off-chunk-load");
    public ItemStack turnOnChunkLoadItem = plugin.config.getItemStack("menu.admin-section.items.turn-on-chunk-load");
    public ItemStack turnOffChunkLoadLogsItem = plugin.config.getItemStack("menu.admin-section.items.turn-off-chunk-load-logs");
    public ItemStack turnOnChunkLoadLogsItem = plugin.config.getItemStack("menu.admin-section.items.turn-on-chunk-load-logs");

    public void openMainMenu(Player player){
        plugin.getPlayersInMenu().remove(player);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.openInventory(mainMenu());
                plugin.getPlayersInMenu().put(player, Menu.MAIN);
            }
        }, 1);
    }

    public void openChunkListMenu(Player player, int page){
        plugin.getPlayersInMenu().remove(player);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.openInventory(chunkListMenu(page));
                plugin.getPlayersInMenu().put(player, Menu.CHUNK_LIST);
            }
        }, 1);
    }

    public void openAdminSectionMenu(Player player){
        plugin.getPlayersInMenu().remove(player);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.openInventory(adminSectionMenu());
                plugin.getPlayersInMenu().put(player, Menu.ADMIN_SECTION);
            }
        }, 1);
    }

    private Inventory mainMenu(){
        Inventory menuInventory = Bukkit.createInventory(null, 36, mainMenuTitle);
        for(int i = 0; i < 36; i++){
            menuInventory.setItem(i, fillItem);
        }
        menuInventory.setItem(11, chunkListItem);
        menuInventory.setItem(15, adminSectionItem);
        menuInventory.setItem(31, closeItem);
        return menuInventory;
    }

    private Inventory chunkListMenu(int page){
        ChunkManager chunkManager = plugin.getChunkManager();
        Inventory chunkListInventory = Bukkit.createInventory(null, 54, chunkListMenuTitle+" "+page);
        for(int i = 0; i < 54; i++){
            chunkListInventory.setItem(i, fillItem);
        }
        for(int i = 0; i<45; i++){
            try{
                chunkListInventory.setItem(i, getChunkListedItem(chunkManager.getChunkStringList().get((page*45)-45+i)));
            }catch (Exception e){}
        }
        if(page!=1){
            chunkListInventory.setItem(45, previousPageItem);
        }
        if(page*45 < chunkManager.getChunkStringList().size()){
            chunkListInventory.setItem(53, nextPageItem);
        }
        chunkListInventory.setItem(48, backItem);
        chunkListInventory.setItem(49, closeItem);
        return chunkListInventory;
    }

    private Inventory adminSectionMenu(){
        ChunkManager chunkManager = plugin.getChunkManager();
        Inventory adminSectionInventory = Bukkit.createInventory(null, 36, adminSectionMenuTitle);
        for(int i = 0; i < 36; i++){
            adminSectionInventory.setItem(i, fillItem);
        }
        if(chunkManager.isChunkLoadEnable()){
            adminSectionInventory.setItem(11, turnOffChunkLoadItem);
        }else{
            adminSectionInventory.setItem(11, turnOnChunkLoadItem);
        }
        if(chunkManager.isChunkLoadLogsEnable()){
            adminSectionInventory.setItem(15, turnOffChunkLoadLogsItem);
        }else{
            adminSectionInventory.setItem(15, turnOnChunkLoadLogsItem);
        }
        adminSectionInventory.setItem(30, backItem);
        adminSectionInventory.setItem(31, closeItem);
        return adminSectionInventory;
    }

    private ItemStack getChunkListedItem(String chunk){
        FileConfiguration config = plugin.config.getFileConfiguration();
        ChunkManager chunkManager = plugin.getChunkManager();
        int chunkIndex = chunkManager.getChunkStringList().indexOf(chunk);
        ItemMeta chunkListedItem_Meta = chunkListedItem.getItemMeta();
        chunkListedItem_Meta.setDisplayName(Color.convert(config.getString("menu.chunk-list.items.listed.display-name")
                .replaceAll("%index%", String.valueOf(chunkIndex+1))));
        List<String> lore = config.getStringList("menu.chunk-list.items.listed.lore");
        lore.replaceAll(line ->
            line.replaceAll("%coords_x%", String.valueOf(chunkManager.unformatChunk(chunkManager.formatChunk(chunkManager.chunkStringList.get(chunkIndex))).getX()))
                .replaceAll("%coords_z%", String.valueOf(chunkManager.unformatChunk(chunkManager.formatChunk(chunkManager.chunkStringList.get(chunkIndex))).getZ()))
                .replaceAll("%coords_world%", chunkManager.unformatChunk(chunkManager.formatChunk(chunkManager.chunkStringList.get(chunkIndex))).getWorld().getName()));
        chunkListedItem_Meta.setLore(Color.convert(lore));
        chunkListedItem.setItemMeta(chunkListedItem_Meta);
        return chunkListedItem;
    }
}
