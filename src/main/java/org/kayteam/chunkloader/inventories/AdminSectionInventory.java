package org.kayteam.chunkloader.inventories;

import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.inventoryapi.InventoryBuilder;
import org.kayteam.storageapi.storage.YML;

public class AdminSectionInventory extends InventoryBuilder {

    public AdminSectionInventory() {
        super(ChunkLoader.config.getString("menu.admin-section.title"), 4);
        YML config = ChunkLoader.config;
        // Fill
        fillItem(() -> config.getItemStack("menu.general-options.items.fill"));
        // Back
        addItem(30, () -> config.getItemStack("menu.general-options.items.back"));
        addLeftAction(30, (player1, slot) -> ChunkLoader.getInventoryManager().openInventory(player1, new MainMenuInventory()));
        // Close
        addItem(31, () -> config.getItemStack("menu.general-options.items.close"));
        addLeftAction(31, (player1, slot) -> player1.closeInventory());
        // Chunk Load
        if (ChunkLoader.getChunkManager().isChunkLoad()) {
            addItem(11, () -> config.getItemStack("menu.admin-section.items.turn-off-chunk-load"));
            addLeftAction(11, ((player, i) -> {
                ChunkLoader.getChunkManager().disableChunkLoad();
                ChunkLoader.getInventoryManager().openInventory(player, new AdminSectionInventory());
                ChunkLoader.messages.sendMessage(player, "chunkloader.disabled");
            }));
        } else {
            addItem(11, () -> config.getItemStack("menu.admin-section.items.turn-on-chunk-load"));
            addLeftAction(11, ((player, i) -> {
                ChunkLoader.getChunkManager().enableChunkLoad();
                ChunkLoader.getInventoryManager().openInventory(player, new AdminSectionInventory());
                ChunkLoader.messages.sendMessage(player, "chunkloader.enabled");
            }));
        }
        // Chunk Load Logs
        if (ChunkLoader.getChunkManager().isChunkLoadLogs()) {
            addItem(15, () -> config.getItemStack("menu.admin-section.items.turn-off-chunk-load-logs"));
            addLeftAction(15, ((player, i) -> {
                ChunkLoader.getChunkManager().setChunkLoadLogs(false);
                ChunkLoader.getInventoryManager().openInventory(player, new AdminSectionInventory());
                ChunkLoader.messages.sendMessage(player, "logs.disabled");
            }));
        } else {
            addItem(15, () -> config.getItemStack("menu.admin-section.items.turn-on-chunk-load-logs"));
            addLeftAction(15, ((player, i) -> {
                ChunkLoader.getChunkManager().setChunkLoadLogs(true);
                ChunkLoader.getInventoryManager().openInventory(player, new AdminSectionInventory());
                ChunkLoader.messages.sendMessage(player, "logs.enabled");
            }));
        }
    }
}
