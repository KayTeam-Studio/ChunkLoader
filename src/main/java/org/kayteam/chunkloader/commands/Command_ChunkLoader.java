package org.kayteam.chunkloader.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

import java.util.ArrayList;
import java.util.List;

public class Command_ChunkLoader implements CommandExecutor, TabCompleter {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("chunkloader.chunkloader ")){
                Send.playerMessage(player, plugin.messages.getFile().getString("command.no-permissions"));
                return false;
            }
            if(args.length>0){
                if(args[0].equalsIgnoreCase("help")){
                    new Command_Help().chunkHelp(player);
                }else if(args[0].equalsIgnoreCase("list")){
                    new Command_List().chunkList(player);
                }else if(args[0].equalsIgnoreCase("reload")){
                    new Command_Reload().commandReload(player);
                }else if(args[0].equalsIgnoreCase("on")){
                    new Command_On().enableChunkLoad(player);
                }else if(args[0].equalsIgnoreCase("off")){
                    new Command_Off().disableChunkLoad(player);
                }else if(args[0].equalsIgnoreCase("tp")){
                    if(args.length>1){
                        new Command_TP().chunkTeleport(player,args[1]);
                    }else{
                        Send.playerMessage(player,plugin.prefix+"&c/cl tp <list-number>");
                    }
                }else if(args[0].equalsIgnoreCase("menu")){
                    plugin.CMD_Menu.openMainMenu(player);
                }else{
                    new Command_Help().chunkHelp(player);
                }
            }else{
                new Command_Help().chunkHelp(player);
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender s, Command c, String label, String[] args) {
        ArrayList<String> tabs = new ArrayList<>();
        if(args.length == 1){
            tabs.add("help");
            tabs.add("list");
            tabs.add("menu");
            tabs.add("on");
            tabs.add("off");
            tabs.add("tp");
            tabs.add("reload");
            return tabs;
        }else
        if(args.length == 2){
            if(args[0].equals("tp")){
                for(int i = 0; i < plugin.getChunkManager().getChunkStringList().size(); i++){
                    tabs.add(String.valueOf(i+1));
                }
            return tabs;
            }
        }
        return null;
    }
}
