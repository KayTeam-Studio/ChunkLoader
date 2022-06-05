package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.inventories.MainMenuInventory;
import org.kayteam.chunkloader.main.MiPlugin;

public class Command_Menu {

    private final MiPlugin plugin;

    public Command_Menu(MiPlugin plugin) {
        this.plugin = plugin;
    }

    public void openMenu(Player player){
        plugin.getInventoryManager().openInventory(player, new MainMenuInventory(plugin));
    }
}
