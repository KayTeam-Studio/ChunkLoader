package org.kayteam.chunkloader.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.kayteam.chunkloader.commands.Command_Menu;
import org.kayteam.chunkloader.commands.Command_TP;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;
import org.kayteam.chunkloader.menus.Menu;
import org.kayteam.chunkloader.util.Send;

public class Inventory_Menu implements Listener {

    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    private ChunkManager chunkManager = plugin.getChunkManager();

    @EventHandler
    public void menuInteract(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(plugin.getPlayersInMenu().containsKey(player)){
            try{
                Command_Menu menu = plugin.CMD_Menu;
                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();
                if(clickedItem.equals(menu.closeItem)){
                    closeInventory(player);
                }
                if(clickedItem.equals(menu.backItem)){
                    if(plugin.getPlayersInMenu().get(player).equals(Menu.ADMIN_SECTION) || plugin.getPlayersInMenu().get(player).equals(Menu.CHUNK_LIST)){
                        menu.openMainMenu(player);
                    }
                }
                if(plugin.getPlayersInMenu().get(player).equals(Menu.MAIN)){
                    if(clickedItem.equals(menu.chunkListItem)){
                        menu.openChunkListMenu(player, 1);
                    }else if(clickedItem.equals(menu.adminSectionItem)) {
                        menu.openAdminSectionMenu(player);
                    }
                }else if(plugin.getPlayersInMenu().get(player).equals(Menu.CHUNK_LIST)){
                    if(clickedItem.getType().equals(menu.chunkListedItem.getType())){
                        if(event.getClick().isLeftClick()){
                            closeInventory(player);
                            new Command_TP().chunkTeleport(player, String.valueOf((getPage(event.getView().getTitle())-1)*45+event.getRawSlot()+1));
                        }else if(event.getClick().isRightClick()){
                            closeInventory(player);
                            menu.openChunkListMenu(player, getPage(event.getView().getTitle()));
                            chunkManager.deleteChunk(chunkManager.unformatChunk(chunkManager.formatChunk(chunkManager.getChunkStringList().get((getPage(event.getView().getTitle())-1)*45+event.getRawSlot()))), player);
                        }
                    }else if(clickedItem.equals(menu.nextPageItem)){
                        menu.openChunkListMenu(player, getPage(event.getView().getTitle())+1);
                    }else if(clickedItem.equals(menu.previousPageItem)){
                        menu.openChunkListMenu(player, getPage(event.getView().getTitle())-1);
                    }
                }else if(plugin.getPlayersInMenu().get(player).equals(Menu.ADMIN_SECTION)){
                    if(clickedItem.equals(menu.turnOnChunkLoadItem)){
                        chunkManager.enableChunkLoad();
                        Send.playerMessage(player,plugin.prefix+plugin.messages.getString("chunkloader.enabled"));
                        player.closeInventory();
                    }else if(clickedItem.equals(menu.turnOffChunkLoadItem)){
                        chunkManager.disableChunkLoad();
                        Send.playerMessage(player,plugin.prefix+plugin.messages.getString("chunkloader.disabled"));
                        player.closeInventory();
                    }else if(clickedItem.equals(menu.turnOnChunkLoadLogsItem)){
                        chunkManager.setChunkLoadLogs(true);
                        Send.playerMessage(player,plugin.prefix+plugin.messages.getString("logs.enabled"));
                        player.closeInventory();
                    }else if(clickedItem.equals(menu.turnOffChunkLoadLogsItem)){
                        chunkManager.setChunkLoadLogs(false);
                        Send.playerMessage(player,plugin.prefix+plugin.messages.getString("logs.disabled"));
                        player.closeInventory();
                    }
                }
            }catch (Exception e){}
        }
    }

    @EventHandler
    public void menuClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(plugin.getPlayersInMenu().containsKey(player)){
            Command_Menu menu = plugin.CMD_Menu;
            if(plugin.getPlayersInMenu().get(player).equals(Menu.MAIN)){
                menu.openMainMenu(player);
            }else
            if(plugin.getPlayersInMenu().get(player).equals(Menu.CHUNK_LIST)){
                menu.openChunkListMenu(player, getPage(event.getView().getTitle()));
            }else
            if(plugin.getPlayersInMenu().get(player).equals(Menu.ADMIN_SECTION)){
                menu.openAdminSectionMenu(player);
            }
        }
    }

    private void closeInventory(Player player){
        plugin.getPlayersInMenu().remove(player);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.closeInventory();
            }
        }, 3);
    }

    private int getPage(String pageTitle){
        String page = pageTitle;
        page = pageTitle.replaceAll("[^\\d]", "");
        return Integer.parseInt(page);
    }
}
