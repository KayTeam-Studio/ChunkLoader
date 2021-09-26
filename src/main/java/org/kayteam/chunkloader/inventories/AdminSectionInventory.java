package org.kayteam.chunkloader.inventories;

import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class AdminSectionInventory extends InventoryBuilder {

    public AdminSectionInventory(ChunkLoader plugin) {
        super(plugin.config.getString("menu.admin-section.title"), 4);
        Yaml config = plugin.config;
        // Fill
        fillItem(() -> config.getItemStack("menu.general-options.items.fill"));
        // Back
        addItem(30, () -> config.getItemStack("menu.general-options.items.back"));
        addLeftAction(30, (player1, slot) -> plugin.getInventoryManager().openInventory(player1, new MainMenuInventory(plugin)));
        // Close
        addItem(31, () -> config.getItemStack("menu.general-options.items.close"));
        addLeftAction(31, (player1, slot) -> player1.closeInventory());
        // Chunk Load
        if(plugin.getChunkManager().isChunkLoad()){
            addItem(11, () -> config.getItemStack("menu.admin-section.items.turn-off-chunk-load"));
            addLeftAction(11, ((player, i) -> {
                plugin.getChunkManager().disableChunkLoad();
                player.closeInventory();
                plugin.messages.sendMessage(player, "chunkloader.disabled");
            }));
        }else{
            addItem(11, () -> config.getItemStack("menu.admin-section.items.turn-on-chunk-load"));
            addLeftAction(11, ((player, i) -> {
                plugin.getChunkManager().enableChunkLoad();
                player.closeInventory();
                plugin.messages.sendMessage(player, "chunkloader.enabled");
            }));
        }
        // Chunk Load Logs
        if(plugin.getChunkManager().isChunkLoadLogs()){
            addItem(15, () -> config.getItemStack("menu.admin-section.items.turn-off-chunk-load-logs"));
            addLeftAction(15, ((player, i) -> {
                plugin.getChunkManager().setChunkLoadLogs(false);
                player.closeInventory();
                plugin.messages.sendMessage(player, "logs.disabled");
            }));
        }else{
            addItem(15, () -> config.getItemStack("menu.admin-section.items.turn-on-chunk-load-logs"));
            addLeftAction(15, ((player, i) -> {
                plugin.getChunkManager().setChunkLoadLogs(true);
                player.closeInventory();
                plugin.messages.sendMessage(player, "logs.enabled");
            }));
        }
    }
}
