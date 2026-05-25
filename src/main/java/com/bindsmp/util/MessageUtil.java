package com.bindsmp.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtil {

    private static final String PREFIX = ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE
            + "BindSMP" + ChatColor.DARK_PURPLE + "] " + ChatColor.RESET;

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void send(CommandSender sender, String msg) {
        sender.sendMessage(PREFIX + color(msg));
    }

    public static void sendRaw(CommandSender sender, String msg) {
        sender.sendMessage(color(msg));
    }

    public static void actionBar(Player player, String msg) {
        player.sendActionBar(net.kyori.adventure.text.Component.text(
                ChatColor.translateAlternateColorCodes('&', msg)));
    }

    public static void title(Player player, String title, String subtitle, int stay) {
        player.sendTitle(color(title), color(subtitle), 10, stay, 15);
    }

    public static void broadcast(String msg) {
        Bukkit.broadcastMessage(PREFIX + color(msg));
    }

    public static void broadcastRaw(String msg) {
        Bukkit.broadcastMessage(color(msg));
    }

    public static String formatCooldown(long millis) {
        if (millis <= 0) return "&aReady";
        return "&e" + String.format("%.1fs", millis / 1000.0);
    }

    public static String bar(int current, int max, int length,
                              ChatColor fill, ChatColor empty) {
        int filled = (int)((double) current / max * length);
        StringBuilder sb = new StringBuilder(ChatColor.BOLD.toString());
        sb.append(fill);
        sb.append("|".repeat(Math.max(0, filled)));
        sb.append(empty);
        sb.append("|".repeat(Math.max(0, length - filled)));
        return sb.toString();
    }
}