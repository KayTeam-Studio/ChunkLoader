package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.MiPlugin;

public class Command_Reload {

    private final MiPlugin plugin;

    public Command_Reload(MiPlugin plugin) {
        this.plugin = plugin;
    }

    public void commandReload(Player player){
        plugin.data.reloadFileConfiguration();
        plugin.messages.reloadFileConfiguration();
        plugin.config.registerFileConfiguration();
        plugin.messages.sendMessage(player, "chunkloader.reload");
    }
}
