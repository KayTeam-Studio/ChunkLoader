package org.kayteam.chunkloader.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kayteam.chunkloader.main.MiPlugin;
import org.kayteam.chunkloader.util.Color;
import org.kayteam.kayteamapi.updatechecker.UpdateChecker;

public class OPJoin implements Listener {
    private final MiPlugin plugin;

    public OPJoin(MiPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onOPJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.isOp()){
            if(plugin.getUpdateChecker().getUpdateCheckResult().equals(UpdateChecker.UpdateCheckResult.OUT_DATED)){
                player.sendMessage(Color.convert(plugin.logPrefix+"There is a new update available.&f&o https://www.spigotmc.org/resources/92834/"));
            }
        }
    }
}
