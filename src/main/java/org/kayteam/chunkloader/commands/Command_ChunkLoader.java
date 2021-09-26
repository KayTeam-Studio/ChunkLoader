package org.kayteam.chunkloader.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.main.ChunkLoader;

import java.util.ArrayList;
import java.util.List;

public class Command_ChunkLoader implements CommandExecutor, TabCompleter {

    private final ChunkLoader plugin;

    public Command_ChunkLoader(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("chunkloader.chunkloader ")){
                plugin.messages.sendMessage(player, "command.no-permissions");
                return false;
            }
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
                        try{
                            new Command_TP(plugin).chunkTeleport(player,Integer.parseInt(args[1]));
                        }catch (Exception ignored){}
                    }else{
                        plugin.messages.sendSimpleMessage(player, "&c/cl tp <list-number>");
                    }
                }else if(args[0].equalsIgnoreCase("menu")){
                    new Command_Menu(plugin).openMenu(player);
                }else{
                    new Command_Help(plugin).chunkHelp(player);
                }
            }else{
                new Command_Help(plugin).chunkHelp(player);
            }
        }
        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command c, @NotNull String label, String[] args) {
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
                    tabs.add(String.valueOf(i));
                }
            return tabs;
            }
        }
        return null;
    }
}
