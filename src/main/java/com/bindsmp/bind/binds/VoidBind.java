package com.bindsmp.bind.binds;

import com.bindsmp.BindSMP;
import com.bindsmp.bind.Bind;
import com.bindsmp.bind.BindType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;

public class VoidBind extends Bind {
    public VoidBind(BindSMP plugin) { super(plugin, BindType.VOID); }
    @Override
    public void onActivate(Player player, boolean isPrimary) {}
    @Override
    public void onUltimate(Player player, boolean isPrimary) {}
    @Override
    public ItemStack createRelic() {
        ItemStack item = new ItemStack(Material.ENDER_EYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(net.kyori.adventure.text.Component.text(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Void Relic"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "An eye that stares into nothing.");
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "bind_type"), PersistentDataType.STRING, "VOID");
        item.setItemMeta(meta);
        return item;
    }
    @Override
    public String[] getDescription() {
        return new String[]{"Void Bind"};
    }
}