package org.kayteam.chunkloader.inventories;

import org.kayteam.api.inventory.InventoryBuilder;
import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.storageapi.storage.Yaml;

public class MainMenuInventory extends InventoryBuilder {

    public MainMenuInventory(){
        super(ChunkLoader.config.getString("menu.main-menu.title"),4);
        Yaml config = ChunkLoader.config;
        // Fill
        fillItem(() -> config.getItemStack("menu.general-options.items.fill"));
        // Close
        addItem(31, () -> config.getItemStack("menu.general-options.items.close"));
        addLeftAction(31, (player1, slot) -> player1.closeInventory());
        // ChunkList Item
        addItem(11, () -> config.getItemStack("menu.main-menu.items.chunk-list"));
        addLeftAction(11, ((player1, i) -> ChunkLoader.getInventoryManager().openInventory(player1, new ChunksListInventory(1))));
        // AdminSection Item
        addItem(15, () -> config.getItemStack("menu.main-menu.items.admin-section"));
        addLeftAction(15, ((player1, i) -> ChunkLoader.getInventoryManager().openInventory(player1, new AdminSectionInventory())));
    }
}
