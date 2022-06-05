package org.kayteam.chunkloader.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.main.MiPlugin;

public class ComandoYoutube implements CommandExecutor {

    MiPlugin plugin;

    public ComandoYoutube(MiPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No puedes ejecutar comandos desde la consola.");
            return false;
        } else {
            Player jugador = (Player) sender;
            jugador.sendMessage(ChatColor.RED + "----------------------------------");
            jugador.sendMessage(ChatColor.YELLOW + "Visita nuestro canal de youtube: https://www.youtube.com/c/Ajneb97");
            jugador.sendMessage(ChatColor.RED + "----------------------------------");
            return true;
        }
    }
}
