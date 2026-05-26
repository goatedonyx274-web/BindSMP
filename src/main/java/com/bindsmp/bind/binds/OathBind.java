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

public class OathBind extends Bind {
    public OathBind(BindSMP plugin) { super(plugin, BindType.OATH); }
    @Override
    public void onActivate(Player player, boolean isPrimary) {}
    @Override
    public void onUltimate(Player player, boolean isPrimary) {}
    public void applyProtectionToTrusted(Player oathHolder, Player trustedVictim, Player attacker, org.bukkit.event.entity.EntityDamageByEntityEvent event, boolean isPrimary) {}
    @Override
    public ItemStack createRelic() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(net.kyori.adventure.text.Component.text(ChatColor.GOLD + "" + ChatColor.BOLD + "Oath Relic"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A golden ingot sealed in sworn blood.");
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "bind_type"), PersistentDataType.STRING, "OATH");
        item.setItemMeta(meta);
        return item;
    }
    @Override
    public String[] getDescription() {
        return new String[]{"Oath Bind"};
    }
}