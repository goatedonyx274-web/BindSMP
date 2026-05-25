package com.bindsmp.util;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

/**
 * Centralised visual effect helpers.
 * All heavy, PvP-visible, vanilla-only effects.
 */
public class EffectUtil {

    public static void spawnRing(Location center, Particle particle, int points,
                                  double radius, double offsetY, Object data) {
        World w = center.getWorld();
        if (w == null) return;
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            Location loc = new Location(w, x, center.getY() + offsetY, z);
            if (data != null) {
                w.spawnParticle(particle, loc, 1, 0, 0, 0, 0, data);
            } else {
                w.spawnParticle(particle, loc, 1, 0, 0, 0, 0);
            }
        }
    }

    public static void spawnRing(Location center, Particle particle, int points, double radius) {
        spawnRing(center, particle, points, radius, 0, null);
    }

    public static void spawnSphere(Location center, Particle particle, int points, double radius) {
        World w = center.getWorld();
        if (w == null) return;
        for (int i = 0; i < points; i++) {
            double phi = Math.acos(-1 + (2.0 * i) / points);
            double theta = Math.sqrt(points * Math.PI) * phi;
            double x = center.getX() + radius * Math.sin(phi) * Math.cos(theta);
            double y = center.getY() + radius * Math.cos(phi);
            double z = center.getZ() + radius * Math.sin(phi) * Math.sin(theta);
            w.spawnParticle(particle, new Location(w, x, y, z), 1, 0, 0, 0, 0);
        }
    }

    public static void spawnHelix(Location center, Particle particle, int steps,
                                    double radius, double height) {
        World w = center.getWorld();
        if (w == null) return;
        for (int i = 0; i < steps; i++) {
            double t = (double) i / steps;
            double angle = t * 4 * Math.PI;
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + t * height;
            double z = center.getZ() + radius * Math.sin(angle);
            w.spawnParticle(particle, new Location(w, x, y, z), 1, 0, 0, 0, 0);
        }
    }

    public static void playSound(Location loc, Sound sound, float volume, float pitch) {
        World w = loc.getWorld();
        if (w != null) w.playSound(loc, sound, volume, pitch);
    }

    public static void playSoundToNearby(Location loc, Sound sound, float volume, float pitch) {
        World w = loc.getWorld();
        if (w == null) return;
        Collection<? extends Player> players = w.getPlayers();
        for (Player p : players) {
            if (p.getLocation().distanceSquared(loc) <= 64 * 64) {
                p.playSound(loc, sound, volume, pitch);
            }
        }
    }

    public static void lightningEffect(Location loc) {
        if (loc.getWorld() != null) {
            loc.getWorld().strikeLightningEffect(loc);
        }
    }

    public static void lightningReal(Location loc) {
        if (loc.getWorld() != null) {
            loc.getWorld().strikeLightning(loc);
        }
    }

    public static Particle.DustOptions dust(Color color, float size) {
        return new Particle.DustOptions(color, size);
    }

    public static void soulActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnRing(center, Particle.SOUL, 24, 2.5);
        spawnRing(center, Particle.SOUL_FIRE_FLAME, 16, 1.8, 0.5, null);
        w.spawnParticle(Particle.DUST, center.clone().add(0,1,0), 40,
                0.5, 1.0, 0.5, 0.05, dust(Color.PURPLE, 2.0f));
        playSound(center, Sound.BLOCK_SOUL_SAND_BREAK, 1.5f, 0.5f);
        playSound(center, Sound.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 1.0f, 1.0f);
    }

    public static void soulUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.SOUL, 60, 4.0);
        spawnSphere(center, Particle.SOUL_FIRE_FLAME, 40, 3.0);
        w.spawnParticle(Particle.DUST, center.clone().add(0,1.5,0), 80,
                1.5, 1.5, 1.5, 0, dust(Color.PURPLE, 3.0f));
        lightningEffect(center);
        playSound(center, Sound.ENTITY_WITHER_SHOOT, 1.5f, 0.3f);
        playSound(center, Sound.BLOCK_SOUL_SAND_BREAK, 2.0f, 0.3f);
    }

    public static void chainActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.ELECTRIC_SPARK, 30, 1.5);
        w.spawnParticle(Particle.CRIT, center, 20, 0.5, 0.5, 0.5, 0.2);
        playSound(center, Sound.BLOCK_CHAIN_BREAK, 1.5f, 0.8f);
        playSound(center, Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.5f);
    }

    public static void chainUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnRing(center, Particle.ELECTRIC_SPARK, 40, 7.0);
        spawnRing(center, Particle.ELECTRIC_SPARK, 30, 4.0);
        spawnRing(center, Particle.ELECTRIC_SPARK, 20, 2.0);
        w.spawnParticle(Particle.CRIT, center, 60, 2.0, 1.0, 2.0, 0.5);
        lightningEffect(center);
        playSound(center, Sound.BLOCK_CHAIN_BREAK, 2.0f, 0.5f);
        playSound(center, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.5f, 0.8f);
    }

    public static void bloodActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        w.spawnParticle(Particle.DUST, center.clone().add(0,1,0), 60,
                0.8, 1.0, 0.8, 0, dust(Color.RED, 2.5f));
        w.spawnParticle(Particle.DRIPPING_LAVA, center, 30, 0.5, 0.5, 0.5, 0.1);
        spawnRing(center, Particle.DUST, 20, 2.0, 0,
                dust(Color.fromRGB(139, 0, 0), 2.0f));
        playSound(center, Sound.ENTITY_PLAYER_HURT, 1.5f, 0.6f);
        playSound(center, Sound.BLOCK_LAVA_POP, 1.0f, 0.8f);
    }

    public static void bloodUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.DUST, 80, 5.0);
        w.spawnParticle(Particle.DUST, center, 100,
                2.0, 2.0, 2.0, 0, dust(Color.RED, 3.0f));
        w.spawnParticle(Particle.DRIPPING_LAVA, center, 60, 1.5, 1.5, 1.5, 0.2);
        playSound(center, Sound.ENTITY_RAVAGER_ROAR, 1.5f, 0.5f);
        playSound(center, Sound.ENTITY_PLAYER_HURT, 2.0f, 0.3f);
    }

    public static void oathActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnRing(center, Particle.DUST, 30, 2.5, 0, dust(Color.YELLOW, 2.5f));
        spawnRing(center, Particle.DUST, 30, 2.5, 1.0, dust(Color.GOLD, 2.0f));
        w.spawnParticle(Particle.ENCHANT, center.clone().add(0,1,0), 50, 0.5, 1.0, 0.5, 1.0);
        playSound(center, Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.5f);
        playSound(center, Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.8f);
    }

    public static void oathUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.DUST, 80, 4.0);
        w.spawnParticle(Particle.DUST, center.clone().add(0,1,0), 100,
                2.0, 2.0, 2.0, 0, dust(Color.YELLOW, 3.0f));
        lightningEffect(center);
        playSound(center, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.5f, 1.5f);
        playSound(center, Sound.BLOCK_BEACON_POWER_SELECT, 1.5f, 0.8f);
    }

    public static void shadowActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        w.spawnParticle(Particle.SQUID_INK, center.clone().add(0,1,0), 30, 0.3, 0.5, 0.3, 0.05);
        w.spawnParticle(Particle.ASH, center, 40, 0.5, 1.0, 0.5, 0.1);
        w.spawnParticle(Particle.SMOKE, center.clone().add(0,1,0), 20, 0.3, 0.5, 0.3, 0.08);
        playSound(center, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.5f);
    }

    public static void shadowUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.SQUID_INK, 60, 2.5);
        w.spawnParticle(Particle.ASH, center, 80, 1.5, 1.5, 1.5, 0.2);
        w.spawnParticle(Particle.SMOKE, center, 40, 1.0, 1.0, 1.0, 0.1);
        playSound(center, Sound.ENTITY_ENDERMAN_TELEPORT, 1.5f, 0.5f);
        playSound(center, Sound.AMBIENT_CAVE, 1.0f, 0.5f);
    }

    public static void flameActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnRing(center, Particle.FLAME, 30, 3.0);
        w.spawnParticle(Particle.LAVA, center.clone().add(0,1,0), 30, 0.5, 1.0, 0.5, 0.1);
        w.spawnParticle(Particle.FLAME, center.clone().add(0,1,0), 50, 0.5, 1.0, 0.5, 0.1);
        playSound(center, Sound.ENTITY_BLAZE_SHOOT, 1.5f, 0.8f);
        playSound(center, Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);
    }

    public static void flameUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.FLAME, 80, 5.0);
        spawnSphere(center, Particle.LAVA, 40, 4.0);
        w.spawnParticle(Particle.FLAME, center.clone().add(0,1,0), 120, 2.5, 2.5, 2.5, 0.3);
        w.spawnParticle(Particle.LARGE_SMOKE, center, 40, 2.0, 2.0, 2.0, 0.1);
        playSound(center, Sound.ENTITY_BLAZE_AMBIENT, 2.0f, 0.3f);
        playSound(center, Sound.ENTITY_GENERIC_EXPLODE, 1.5f, 0.5f);
    }

    public static void frostActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnRing(center, Particle.SNOWFLAKE, 30, 3.5);
        w.spawnParticle(Particle.CLOUD, center.clone().add(0,0.5,0), 40, 1.5, 0.5, 1.5, 0.05);
        w.spawnParticle(Particle.ITEM_SNOWBALL, center.clone().add(0,1,0), 30, 0.8, 0.8, 0.8, 0.2);
        playSound(center, Sound.BLOCK_GLASS_BREAK, 1.5f, 1.8f);
        playSound(center, Sound.ITEM_TRIDENT_THROW, 1.0f, 1.5f);
    }

    public static void frostUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.SNOWFLAKE, 120, 7.0);
        w.spawnParticle(Particle.CLOUD, center.clone().add(0,1,0), 80, 3.5, 2.0, 3.5, 0.1);
        w.spawnParticle(Particle.ITEM_SNOWBALL, center, 60, 3.0, 2.0, 3.0, 0.3);
        playSound(center, Sound.WEATHER_RAIN, 2.0f, 0.5f);
        playSound(center, Sound.BLOCK_POWDER_SNOW_BREAK, 2.0f, 0.5f);
    }

    public static void stormActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        w.spawnParticle(Particle.ELECTRIC_SPARK, center.clone().add(0,1,0), 60, 0.5, 2.0, 0.5, 0.2);
        lightningEffect(center);
        playSound(center, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 2.0f, 0.8f);
        playSound(center, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1.5f, 1.0f);
    }

    public static void stormUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        w.spawnParticle(Particle.ELECTRIC_SPARK, center, 120, 4.0, 2.0, 4.0, 0.3);
        w.spawnParticle(Particle.FLASH, center, 3, 0, 0, 0, 0);
        playSound(center, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 3.0f, 0.5f);
    }

    public static void voidActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.PORTAL, 60, 2.0);
        w.spawnParticle(Particle.REVERSE_PORTAL, center.clone().add(0,1,0), 40, 0.5, 1.0, 0.5, 0.3);
        w.spawnParticle(Particle.SQUID_INK, center.clone().add(0,1,0), 20, 0.3, 0.3, 0.3, 0.05);
        playSound(center, Sound.ENTITY_ENDERMAN_STARE, 1.0f, 0.5f);
        playSound(center, Sound.BLOCK_PORTAL_AMBIENT, 1.5f, 0.5f);
    }

    public static void voidUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.PORTAL, 120, 7.0);
        spawnSphere(center, Particle.REVERSE_PORTAL, 80, 5.0);
        w.spawnParticle(Particle.SQUID_INK, center, 80, 3.0, 3.0, 3.0, 0.2);
        w.spawnParticle(Particle.FLASH, center, 5, 0, 0, 0, 0);
        playSound(center, Sound.ENTITY_WITHER_SPAWN, 1.5f, 0.3f);
        playSound(center, Sound.BLOCK_PORTAL_TRAVEL, 1.0f, 0.3f);
    }

    public static void stoneActivate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        w.spawnParticle(Particle.BLOCK, center.clone().add(0,1,0), 80, 0.5, 1.0, 0.5, 0.3,
                Material.STONE.createBlockData());
        spawnRing(center, Particle.CRIT, 24, 2.0);
        playSound(center, Sound.BLOCK_STONE_BREAK, 1.5f, 0.5f);
        playSound(center, Sound.ENTITY_IRON_GOLEM_HURT, 1.0f, 1.5f);
    }

    public static void stoneUltimate(Location center) {
        World w = center.getWorld(); if (w == null) return;
        spawnRing(center, Particle.BLOCK, 40, 5.0, -0.3, Material.STONE.createBlockData());
        spawnRing(center, Particle.BLOCK, 30, 7.0, -0.3, Material.STONE.createBlockData());
        w.spawnParticle(Particle.BLOCK, center, 120, 3.0, 0.5, 3.0, 0.5,
                Material.GRAVEL.createBlockData());
        w.spawnParticle(Particle.CRIT, center.clone().add(0,1,0), 60, 2.5, 1.0, 2.5, 0.3);
        playSound(center, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 0.3f);
        playSound(center, Sound.BLOCK_STONE_BREAK, 2.0f, 0.3f);
    }

    public static void altarRitualTick(Location center, com.bindsmp.bind.BindType type) {
        World w = center.getWorld(); if (w == null) return;
        spawnHelix(center.clone().add(0, -0.5, 0), Particle.SOUL_FIRE_FLAME, 30, 2.0, 4.0);
        w.spawnParticle(Particle.SOUL, center.clone().add(0, 0.5, 0), 8, 0.5, 0.5, 0.5, 0.02);
        Particle.DustOptions color = getBindDustColor(type);
        spawnRing(center, Particle.DUST, 20, 3.5, 0, color);
        spawnRing(center, Particle.DUST, 16, 2.5, 1.5, color);
        if (Math.random() < 0.3) {
            playSound(center, Sound.BLOCK_SOUL_SAND_BREAK, 0.8f, 0.5f + (float)Math.random() * 0.5f);
        }
    }

    public static void altarRitualStart(Location center, com.bindsmp.bind.BindType type) {
        World w = center.getWorld(); if (w == null) return;
        spawnSphere(center, Particle.SOUL_FIRE_FLAME, 60, 3.0);
        spawnSphere(center, Particle.SOUL, 40, 4.0);
        Particle.DustOptions color = getBindDustColor(type);
        w.spawnParticle(Particle.DUST, center.clone().add(0, 1, 0), 100, 2.0, 2.0, 2.0, 0, color);
        lightningEffect(center);
        playSound(center, Sound.ENTITY_WITHER_SPAWN, 1.5f, 0.5f);
        playSound(center, Sound.BLOCK_BEACON_ACTIVATE, 1.5f, 0.5f);
    }

    public static void altarRitualComplete(Location center, com.bindsmp.bind.BindType type) {
        World w = center.getWorld(); if (w == null) return;
        Particle.DustOptions color = getBindDustColor(type);
        spawnSphere(center, Particle.DUST, 120, 5.0);
        w.spawnParticle(Particle.DUST, center.clone().add(0,1,0), 200, 3.0, 3.0, 3.0, 0, color);
        spawnSphere(center, Particle.SOUL_FIRE_FLAME, 80, 4.0);
        lightningEffect(center);
        lightningEffect(center.clone().add(1.5, 0, 0));
        lightningEffect(center.clone().add(-1.5, 0, 0));
        playSound(center, Sound.UI_TOAST_CHALLENGE_COMPLETE, 2.0f, 1.0f);
        playSound(center, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.5f, 1.5f);
    }

    private static Particle.DustOptions getBindDustColor(com.bindsmp.bind.BindType type) {
        return switch (type) {
            case SOUL   -> dust(Color.PURPLE, 2.5f);
            case CHAIN  -> dust(Color.WHITE, 2.0f);
            case BLOOD  -> dust(Color.RED, 2.5f);
            case OATH   -> dust(Color.YELLOW, 2.5f);
            case SHADOW -> dust(Color.fromRGB(40, 40, 40), 2.5f);
            case FLAME  -> dust(Color.ORANGE, 2.5f);
            case FROST  -> dust(Color.AQUA, 2.5f);
            case STORM  -> dust(Color.fromRGB(255, 255, 100), 2.5f);
            case VOID   -> dust(Color.fromRGB(0, 180, 180), 2.5f);
            case STONE  -> dust(Color.GRAY, 2.5f);
        };
    }
}