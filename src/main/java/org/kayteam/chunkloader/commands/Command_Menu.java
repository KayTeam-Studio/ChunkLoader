package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.chunkloader.inventories.MainMenuInventory;

public class Command_Menu {

    public void openMenu(Player player) {
        ChunkLoader.getInventoryManager().openInventory(player, new MainMenuInventory());
    }
}
