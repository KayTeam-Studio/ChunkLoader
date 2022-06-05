package org.kayteam.chunkloader.inventories;

import org.kayteam.chunkloader.main.MiPlugin;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class MainMenuInventory extends InventoryBuilder {

    public MainMenuInventory(MiPlugin plugin){
        super(plugin.config.getString("menu.main-menu.title"),4);
        Yaml config = plugin.config;
        // Fill
        fillItem(() -> config.getItemStack("menu.general-options.items.fill"));
        // Close
        addItem(31, () -> config.getItemStack("menu.general-options.items.close"));
        addLeftAction(31, (player1, slot) -> player1.closeInventory());
        // ChunkList Item
        addItem(11, () -> config.getItemStack("menu.main-menu.items.chunk-list"));
        addLeftAction(11, ((player1, i) -> plugin.getInventoryManager().openInventory(player1, new ChunksListInventory(plugin, 1))));
        // AdminSection Item
        addItem(15, () -> config.getItemStack("menu.main-menu.items.admin-section"));
        addLeftAction(15, ((player1, i) -> plugin.getInventoryManager().openInventory(player1, new AdminSectionInventory(plugin))));
    }
}
