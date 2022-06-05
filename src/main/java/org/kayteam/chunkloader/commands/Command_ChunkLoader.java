package org.kayteam.chunkloader.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.main.MiPlugin;

import java.util.ArrayList;
import java.util.List;

public class Command_ChunkLoader implements CommandExecutor, TabCompleter {

    private final MiPlugin plugin;

    public Command_ChunkLoader(MiPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length>0){
                if(args[0].equalsIgnoreCase("help")){
                    new Command_Help(plugin).chunkHelp(player);
                }else if(args[0].equalsIgnoreCase("list")){
                    if(!player.hasPermission("chunkloader.list")){
                        plugin.messages.sendMessage(player, "command.no-permissions");
                        return false;
                    }
                    new Command_List(plugin).chunkList(player);
                }else if(args[0].equalsIgnoreCase("reload")){
                    if(!player.hasPermission("chunkloader.reload")){
                        plugin.messages.sendMessage(player, "command.no-permissions");
                        return false;
                    }
                    new Command_Reload(plugin).commandReload(player);
                }else if(args[0].equalsIgnoreCase("on")){
                    if(!player.hasPermission("chunkloader.on")){
                        plugin.messages.sendMessage(player, "command.no-permissions");
                        return false;
                    }
                    new Command_On(plugin).enableChunkLoad(player);
                }else if(args[0].equalsIgnoreCase("off")){
                    if(!player.hasPermission("chunkloader.off")){
                        plugin.messages.sendMessage(player, "command.no-permissions");
                        return false;
                    }
                    new Command_Off(plugin).disableChunkLoad(player);
                }else if(args[0].equalsIgnoreCase("tp")){
                    if(!player.hasPermission("chunkloader.tp")){
                        plugin.messages.sendMessage(player, "command.no-permissions");
                        return false;
                    }
                    if(args.length>1){
                        try{
                            new Command_TP(plugin).chunkTeleport(player,Integer.parseInt(args[1]));
                        }catch (Exception ignored){}
                    }else{
                        plugin.messages.sendSimpleMessage(player, "&c/cl tp <list-number>");
                    }
                }else if(args[0].equalsIgnoreCase("menu")){
                    if(!player.hasPermission("chunkloader.menu")){
                        plugin.messages.sendMessage(player, "command.no-permissions");
                        return false;
                    }
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
