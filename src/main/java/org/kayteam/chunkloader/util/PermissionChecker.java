package org.kayteam.chunkloader.util;

import org.bukkit.command.CommandSender;
import org.kayteam.chunkloader.main.ChunkLoader;

public class PermissionChecker {

    public static boolean check(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        ChunkLoader.messages.sendMessage(sender, "command.no-permissions");
        return false;
    }
}
