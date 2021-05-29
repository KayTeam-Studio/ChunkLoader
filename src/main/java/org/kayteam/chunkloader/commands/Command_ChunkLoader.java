package org.kayteam.chunkloader.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

public class Command_ChunkLoader implements CommandExecutor {
    private ChunkLoader plugin;

    public Command_ChunkLoader(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length>0){
                if(args[0].equalsIgnoreCase("help")){
                    new Command_Help(plugin).chunkHelp(player);
                }else if(args[0].equalsIgnoreCase("list")){
                    new Command_List(plugin).chunkList(player);
                }else if(args[0].equalsIgnoreCase("reload")){
                    new Command_Reload(plugin).commandReload(player);
                }else if(args[0].equalsIgnoreCase("on")){
                    new Command_On(plugin).enableChunkLoad(player);
                }else if(args[0].equalsIgnoreCase("off")){
                    new Command_Off(plugin).disableChunkLoad(player);
                }else if(args[0].equalsIgnoreCase("tp")){
                    if(args.length>1){
                        new Command_TP(plugin).chunkTeleport(player,args[1]);
                    }else{
                        Send.playerMessage(player,plugin.prefix+"&c/cl tp <list-number>");
                    }
                }else{
                    new Command_Help(plugin).chunkHelp(player);
                }
            }
        }
        return false;
    }
}
