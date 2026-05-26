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

public class ShadowBind extends Bind {
    public ShadowBind(BindSMP plugin) { super(plugin, BindType.SHADOW); }
    @Override
    public void onActivate(Player player, boolean isPrimary) {}
    @Override
    public void onUltimate(Player player, boolean isPrimary) {}
    @Override
    public ItemStack createRelic() {
        ItemStack item = new ItemStack(Material.BLACK_DYE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(net.kyori.adventure.text.Component.text(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Shadow Relic"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Swallowed by darkness.");
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "bind_type"), PersistentDataType.STRING, "SHADOW");
        item.setItemMeta(meta);
        return item;
    }
    @Override
    public String[] getDescription() {
        return new String[]{"Shadow Bind"};
    }
}