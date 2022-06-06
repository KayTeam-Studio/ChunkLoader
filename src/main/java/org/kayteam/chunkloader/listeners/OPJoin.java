package org.kayteam.chunkloader.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kayteam.api.updatechecker.UpdateChecker;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Color;

public class OPJoin implements Listener {

    @EventHandler
    private void onOPJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.isOp()){
            if(ChunkLoader.getUpdateChecker().getUpdateCheckResult().equals(UpdateChecker.UpdateCheckResult.OUT_DATED)){
                player.sendMessage(Color.convert(ChunkLoader.logPrefix+"There is a new update available.&f&o https://www.spigotmc.org/resources/92834/"));
            }
        }
    }
}
