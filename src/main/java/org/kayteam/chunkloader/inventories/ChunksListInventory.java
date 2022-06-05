package org.kayteam.chunkloader.inventories;

import org.bukkit.Chunk;
import org.kayteam.chunkloader.commands.Command_TP;
import org.kayteam.chunkloader.main.MiPlugin;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.List;

public class ChunksListInventory extends InventoryBuilder {

    public ChunksListInventory(MiPlugin plugin, int page){
        super(plugin.config.getString("menu.chunk-list.title"), 6);
        Yaml config = plugin.config;
        fillItem(() -> config.getItemStack("menu.general-options.items.fill"), new int[] {6});
        // Back
        addItem(48, () -> config.getItemStack("menu.general-options.items.back"));
        addLeftAction(48, ((player1, i) -> plugin.getInventoryManager().openInventory(player1, new MainMenuInventory(plugin))));
        // Close
        addItem(49, () -> config.getItemStack("menu.general-options.items.close"));
        addLeftAction(49, (player1, i) -> player1.closeInventory());
        // Chunks
        List<Chunk> chunks = plugin.getChunkManager().getChunkList();
        for (int i = 0; i < 45; i++) {
            int index = ((page * (5 * 9)) - (5 * 9)) + i;
            if (index < chunks.size()) {
                addItem(i, () -> Yaml.replace(config.getItemStack("menu.chunk-list.items.listed"), new String[][]{
                        {"%index%", String.valueOf(index)},
                        {"%coords_x%", String.valueOf(chunks.get(index).getX())},
                        {"%coords_z%", String.valueOf(chunks.get(index).getZ())},
                        {"%coords_world%", chunks.get(index).getWorld().getName()}
                }));
                addLeftAction(i, (player1, slot) -> {
                    player1.closeInventory();
                    new Command_TP(plugin).chunkTeleport(player1, index);
                });
                addRightAction(i, (player1, slot) -> {
                    plugin.getChunkManager().deleteChunk(chunks.get(index), player1);
                    player1.closeInventory();
                    plugin.getInventoryManager().openInventory(player1, new ChunksListInventory(plugin, page));
                });
            }
        }
        // Previous Page
        if (page > 1) {
            addItem(45, () -> config.getItemStack("menu.general-options.items.previous-page"));
            addLeftAction(45, (player1, slot) -> plugin.getInventoryManager().openInventory(player1, new ChunksListInventory(plugin, page - 1)));
        }
        // Next Page
        if (chunks.size() > (page * (5 * 9))) {
            addItem(53, () -> config.getItemStack("menu.general-options.items.next-page"));
            addLeftAction(53, (player1, slot) -> plugin.getInventoryManager().openInventory(player1, new ChunksListInventory(plugin, page + 1)));
        }
    }
}
