package org.kayteam.chunkloader.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
    public ItemStack closeItem;
    public ItemStack backItem;
    public ItemStack fillItem;
    public ItemStack nextPageItem;
    public ItemStack previousPageItem;

    //MAIN-MENU THINGS
    public String mainMenuTitle = plugin.config.getFile().getString("menu.main-menu.title");
    public ItemStack chunkListItem;
    public ItemStack adminSectionItem;

    //ADMIN-SECTION-MENU THINGS
    public String adminSectionMenuTitle = plugin.config.getFile().getString("menu.admin-section.title");

    //CHUNK-LIST-MENU THINGS
    public String chunkListMenuTitle = plugin.config.getFile().getString("menu.chunk-list.title");
    public ItemStack chunkListedItem;

    //ADMIN-MENU THINGS
    public ItemStack turnOffChunkLoadItem;
    public ItemStack turnOnChunkLoadItem;
    public ItemStack turnOffChunkLoadLogsItem;
    public ItemStack turnOnChunkLoadLogsItem;

    public Command_Menu(){
        FileConfiguration config = plugin.config.getFile();

        //INICIALIZA EL ITEM PARA LISTAR LOS CHUNKS AÑADIDOS
            chunkListItem = new ItemStack(Material.valueOf(config.getString("menu.main-menu.items.chunk-list.material")));
            ItemMeta chunkListItem_Meta = chunkListItem.getItemMeta();
            chunkListItem_Meta.setDisplayName(Color.convert(config.getString("menu.main-menu.items.chunk-list.display-name")));
            chunkListItem_Meta.setLore(Color.convert(config.getStringList("menu.main-menu.items.chunk-list.lore")));
            chunkListItem.setItemMeta(chunkListItem_Meta);

        //INICIALIZA EL ITEM PARA ACCEDER AL MENÚ DE ADMIN
            adminSectionItem = new ItemStack(Material.valueOf(config.getString("menu.main-menu.items.admin-section.material")));
            ItemMeta adminSectionItem_Meta = adminSectionItem.getItemMeta();
            adminSectionItem_Meta.setDisplayName(Color.convert(config.getString("menu.main-menu.items.admin-section.display-name")));
            adminSectionItem_Meta.setLore(Color.convert(config.getStringList("menu.main-menu.items.admin-section.lore")));
            adminSectionItem.setItemMeta(adminSectionItem_Meta);

        //INICIALIZA EL ITEM PARA CERRAR CUALQUIER MENÚ
            closeItem = new ItemStack(Material.valueOf(config.getString("menu.general-options.items.close.material")));
            ItemMeta closeItem_Meta = closeItem.getItemMeta();
            closeItem_Meta.setDisplayName(Color.convert(config.getString("menu.general-options.items.close.display-name")));
            closeItem_Meta.setLore(Color.convert(config.getStringList("menu.general-options.items.close.lore")));
            closeItem.setItemMeta(closeItem_Meta);

        //INICIAL EL ITEM PARA VOLVER AL MENÚ DE ATRÁS
            backItem = new ItemStack(Material.valueOf(config.getString("menu.general-options.items.back.material")));
            ItemMeta backItem_Meta = backItem.getItemMeta();
            backItem_Meta.setDisplayName(Color.convert(config.getString("menu.general-options.items.back.display-name")));
            backItem_Meta.setLore(Color.convert(config.getStringList("menu.general-options.items.back.lore")));
            backItem.setItemMeta(backItem_Meta);

        //INICIALIZA EL ITEM DE RELLENO
            fillItem = new ItemStack(Material.valueOf(config.getString("menu.general-options.items.fill.material")));
            ItemMeta fillItem_Neta  = fillItem.getItemMeta();
            fillItem_Neta.setDisplayName(Color.convert(config.getString("menu.main-menu.items.fill.display-name")));
            fillItem.setItemMeta(fillItem_Neta);

        //INICIALIZA EL ITEM PARA AVANZAR DE PÁGINA
            nextPageItem = new ItemStack(Material.valueOf(config.getString("menu.general-options.items.next-page.material")));
            ItemMeta nextPageItem_Meta = nextPageItem.getItemMeta();
            nextPageItem_Meta.setDisplayName(Color.convert(config.getString("menu.general-options.items.next-page.display-name")));
            nextPageItem_Meta.setLore(Color.convert(config.getStringList("menu.general-options.items.next-page.lore")));
            nextPageItem.setItemMeta(nextPageItem_Meta);

        //INICIALIZA EL ITEM PARA RETROCEDER DE PÁGINA
            previousPageItem = new ItemStack(Material.valueOf(config.getString("menu.general-options.items.previous-page.material")));
            ItemMeta previousPageItem_Meta = previousPageItem.getItemMeta();
            previousPageItem_Meta.setDisplayName(Color.convert(config.getString("menu.general-options.items.previous-page.display-name")));
            previousPageItem_Meta.setLore(Color.convert(config.getStringList("menu.general-options.items.previous-page.lore")));
            previousPageItem.setItemMeta(previousPageItem_Meta);

        //INICIALIZA EL ITEM DE LISTA DE CHUNKS
            chunkListedItem = new ItemStack(Material.valueOf(plugin.config.getFile().getString("menu.chunk-list.items.listed.material")));

        //INICIALIZA EL ITEM DE TURN-ON-CHUNK-LOAD
            turnOnChunkLoadItem = new ItemStack(Material.valueOf(config.getString("menu.admin-section.items.turn-on-chunk-load.material")));
            ItemMeta turnOnChunkLoadItem_Meta = turnOnChunkLoadItem.getItemMeta();
            turnOnChunkLoadItem_Meta.setDisplayName(Color.convert(config.getString("menu.admin-section.items.turn-on-chunk-load.display-name")));
            turnOnChunkLoadItem_Meta.setLore(Color.convert(config.getStringList("menu.admin-section.items.turn-on-chunk-load.lore")));
            turnOnChunkLoadItem.setItemMeta(turnOnChunkLoadItem_Meta);

        //INICIALIZA EL ITEM DE TURN-OFF-CHUNK-LOAD
            turnOffChunkLoadItem = new ItemStack(Material.valueOf(config.getString("menu.admin-section.items.turn-off-chunk-load.material")));
            ItemMeta turnOffChunkLoadItem_Meta = turnOffChunkLoadItem.getItemMeta();
            turnOffChunkLoadItem_Meta.setDisplayName(Color.convert(config.getString("menu.admin-section.items.turn-off-chunk-load.display-name")));
            turnOffChunkLoadItem_Meta.setLore(Color.convert(config.getStringList("menu.admin-section.items.turn-off-chunk-load.lore")));
            turnOffChunkLoadItem.setItemMeta(turnOffChunkLoadItem_Meta);

        //INICIALIZA EL ITEM DE TURN-ON-CHUNK-LOAD-LOGS
            turnOnChunkLoadLogsItem = new ItemStack(Material.valueOf(config.getString("menu.admin-section.items.turn-on-chunk-load-logs.material")));
            ItemMeta turnOnChunkLoadLogsItem_Meta = turnOnChunkLoadLogsItem.getItemMeta();
            turnOnChunkLoadLogsItem_Meta.setDisplayName(Color.convert(config.getString("menu.admin-section.items.turn-on-chunk-load-logs.display-name")));
            turnOnChunkLoadLogsItem_Meta.setLore(Color.convert(config.getStringList("menu.admin-section.items.turn-on-chunk-load-logs.lore")));
            turnOnChunkLoadLogsItem.setItemMeta(turnOnChunkLoadLogsItem_Meta);

        //INICIALIZA EL ITEM DE TURN-OFF-CHUNK-LOAD-LOGS
            turnOffChunkLoadLogsItem = new ItemStack(Material.valueOf(config.getString("menu.admin-section.items.turn-off-chunk-load-logs.material")));
            ItemMeta turnOffChunkLoadLogsItem_Meta = turnOnChunkLoadLogsItem.getItemMeta();
            turnOffChunkLoadLogsItem_Meta.setDisplayName(Color.convert(config.getString("menu.admin-section.items.turn-off-chunk-load-logs.display-name")));
            turnOffChunkLoadLogsItem_Meta.setLore(Color.convert(config.getStringList("menu.admin-section.items.turn-off-chunk-load-logs.lore")));
            turnOffChunkLoadLogsItem.setItemMeta(turnOffChunkLoadLogsItem_Meta);
    }


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
        FileConfiguration config = plugin.config.getFile();
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
