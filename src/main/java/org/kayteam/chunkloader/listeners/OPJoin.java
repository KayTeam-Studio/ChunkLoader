package org.kayteam.chunkloader.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Color;
import org.kayteam.chunkloader.util.UpdateChecker;

public class OPJoin implements Listener {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    @EventHandler
    private void onOPJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.isOp()){
            if(plugin.getUpdateChecker().getUpdateCheckResult().equals(UpdateChecker.UpdateCheckResult.OUT_DATED)){
                player.sendMessage(Color.convert(plugin.prefix+"There is a new update available. &f&ohttps://www.spigotmc.org/resources/chunkloader-keep-your-chunks-loaded.92834/"));
            }
        }
    }
}
