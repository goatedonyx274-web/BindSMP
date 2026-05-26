package com.bindsmp.bind;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import com.bindsmp.BindSMP;

public abstract class Bind {

    protected final BindSMP plugin;
    protected final BindType type;

    public Bind(BindSMP plugin, BindType type) {
        this.plugin = plugin;
        this.type = type;
    }

    public BindType getType() { return type; }

    public abstract void onActivate(Player player, boolean isPrimary);
    public abstract void onUltimate(Player player, boolean isPrimary);

    public void onDamageDealt(Player attacker, Entity victim,
                               EntityDamageByEntityEvent event, boolean isPrimary) {}

    public void onDamageTaken(Player victim, Entity attacker,
                               EntityDamageByEntityEvent event, boolean isPrimary) {}

    public void applyPassive(Player player, boolean isPrimary) {}
    public void removePassive(Player player) {}
    public void onTick(Player player, boolean isPrimary) {}

    public abstract ItemStack createRelic();
    public abstract String[] getDescription();

    public String getActivateCooldownKey() {
        return type.name() + "_activate";
    }

    public String getUltimateCooldownKey() {
        return type.name() + "_ultimate";
    }

    public long getActivateCooldown(boolean isPrimary) {
        long base = plugin.getConfigManager().getActivateCooldown(type);
        return isPrimary ? base : (long)(base * plugin.getConfigManager().getSecondaryCooldownMultiplier());
    }

    public long getUltimateCooldown(boolean isPrimary) {
        long base = plugin.getConfigManager().getUltimateCooldown(type);
        return isPrimary ? base : (long)(base * plugin.getConfigManager().getSecondaryUltimateCooldownMultiplier());
    }

    protected double scaleDmg(double base, boolean isPrimary) {
        return isPrimary ? base : base * plugin.getConfigManager().getSecondaryDamageMultiplier();
    }

    protected int scaleDuration(int baseTicks, boolean isPrimary) {
        return isPrimary ? baseTicks : (int)(baseTicks * plugin.getConfigManager().getSecondaryDurationMultiplier());
    }
}