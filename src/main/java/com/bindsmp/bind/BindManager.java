package com.bindsmp.bind;

import com.bindsmp.BindSMP;
import com.bindsmp.bind.binds.*;
import com.bindsmp.data.PlayerData;
import com.bindsmp.util.MessageUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.*;

public class BindManager {

    private final BindSMP plugin;
    private final Map<BindType, Bind> registry = new HashMap<>();
    private final Map<UUID, UUID> soulLinks = new HashMap<>();

    public BindManager(BindSMP plugin) {
        this.plugin = plugin;
        registerBinds();
    }

    private void registerBinds() {
        registry.put(BindType.SOUL, new SoulBind(plugin));
        registry.put(BindType.CHAIN, new ChainBind(plugin));
        registry.put(BindType.BLOOD, new BloodBind(plugin));
        registry.put(BindType.OATH, new OathBind(plugin));
        registry.put(BindType.SHADOW, new ShadowBind(plugin));
        registry.put(BindType.FLAME, new FlameBind(plugin));
        registry.put(BindType.FROST, new FrostBind(plugin));
        registry.put(BindType.STORM, new StormBind(plugin));
        registry.put(BindType.VOID, new VoidBind(plugin));
        registry.put(BindType.STONE, new StoneBind(plugin));
    }

    public Bind getBind(BindType type) {
        return registry.get(type);
    }

    public void activateSpecific(Player player, BindType type, boolean isUltimate) {
        PlayerData data = plugin.getDataManager().get(player);
        boolean isPrimary = data.isPrimary(type);
        Bind bind = getBind(type);
        if (bind == null) return;

        if (isUltimate) {
            if (!data.isReady(bind.getUltimateCooldownKey())) {
                MessageUtil.actionBar(player, "&cUltimate on cooldown: " + data.getCooldownString(bind.getUltimateCooldownKey()));
                return;
            }
            bind.onUltimate(player, isPrimary);
            data.setCooldown(bind.getUltimateCooldownKey(), bind.getUltimateCooldown(isPrimary));
        } else {
            if (!data.isReady(bind.getActivateCooldownKey())) {
                MessageUtil.actionBar(player, "&cAbility on cooldown: " + data.getCooldownString(bind.getActivateCooldownKey()));
                return;
            }
            bind.onActivate(player, isPrimary);
            data.setCooldown(bind.getActivateCooldownKey(), bind.getActivateCooldown(isPrimary));
        }
    }

    public void applyPassives(Player player) {
        PlayerData data = plugin.getDataManager().get(player);
        if (data.getSlot1() != null) {
            Bind b1 = getBind(data.getSlot1());
            if (b1 != null) b1.applyPassive(player, true);
        }
        if (data.getSlot2() != null) {
            Bind b2 = getBind(data.getSlot2());
            if (b2 != null) b2.applyPassive(player, false);
        }
    }

    public void tickPassives(Player player) {
        PlayerData data = plugin.getDataManager().get(player);
        if (data.getSlot1() != null) {
            Bind b1 = getBind(data.getSlot1());
            if (b1 != null) b1.onTick(player, true);
        }
        if (data.getSlot2() != null) {
            Bind b2 = getBind(data.getSlot2());
            if (b2 != null) b2.onTick(player, false);
        }
    }

    public void handleDamageDealt(Player attacker, Entity victim, EntityDamageByEntityEvent event) {
        PlayerData data = plugin.getDataManager().get(attacker);
        if (data.getSlot1() != null) {
            Bind b1 = getBind(data.getSlot1());
            if (b1 != null) b1.onDamageDealt(attacker, victim, event, true);
        }
        if (data.getSlot2() != null) {
            Bind b2 = getBind(data.getSlot2());
            if (b2 != null) b2.onDamageDealt(attacker, victim, event, false);
        }
    }

    public void handleDamageTaken(Player victim, Entity attacker, EntityDamageByEntityEvent event) {
        PlayerData data = plugin.getDataManager().get(victim);
        if (data.getSlot1() != null) {
            Bind b1 = getBind(data.getSlot1());
            if (b1 != null) b1.onDamageTaken(victim, attacker, event, true);
        }
        if (data.getSlot2() != null) {
            Bind b2 = getBind(data.getSlot2());
            if (b2 != null) b2.onDamageTaken(victim, attacker, event, false);
        }
    }

    public Map<BindType, Bind> getRegistry() { return registry; }
    public boolean hasSoulLink(UUID uuid) { return soulLinks.containsKey(uuid); }
    public void removeSoulLink(UUID uuid) { soulLinks.remove(uuid); }
}