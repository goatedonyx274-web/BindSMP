package com.bindsmp.commands;

import com.bindsmp.BindSMP;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public class TrustCommand implements CommandExecutor, TabCompleter {
    private final BindSMP plugin;
    public TrustCommand(BindSMP plugin) { this.plugin = plugin; }
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        return true;
    }
    @Override
    public java.util.List<String> onTabComplete(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        return java.util.Collections.emptyList();
    }
}