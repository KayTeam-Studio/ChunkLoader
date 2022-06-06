package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.inventories.MainMenuInventory;
import org.kayteam.chunkloader.main.ChunkLoader;

public class Command_Menu {

    public void openMenu(Player player){
        ChunkLoader.getInventoryManager().openInventory(player, new MainMenuInventory());
    }
}
