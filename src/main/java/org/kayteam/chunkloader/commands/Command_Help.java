package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.MiPlugin;

public class Command_Help {

    private final MiPlugin plugin;

    public Command_Help(MiPlugin plugin) {
        this.plugin = plugin;
    }

    public void chunkHelp(Player player){
        plugin.messages.sendMessage(player, "chunkloader.help");
    }
}
