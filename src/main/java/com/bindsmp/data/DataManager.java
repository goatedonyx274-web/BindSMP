package com.bindsmp.data;

import com.bindsmp.BindSMP;
import com.bindsmp.bind.BindType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {

    private final BindSMP plugin;
    private final File dataFolder;
    private final Map<UUID, PlayerData> cache = new HashMap<>();

    public DataManager(BindSMP plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();
    }

    public void load() {
        File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            try {
                UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));
                PlayerData data = loadFromFile(uuid, file);
                cache.put(uuid, data);
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load data file: " + file.getName());
            }
        }
    }

    private PlayerData loadFromFile(UUID uuid, File file) {
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String name = cfg.getString("name", "Unknown");
        PlayerData data = new PlayerData(uuid, name);

        String s1 = cfg.getString("slot1");
        String s2 = cfg.getString("slot2");
        if (s1 != null && !s1.isEmpty()) {
            BindType t = BindType.fromString(s1);
            if (t != null) data.setSlot1(t);
        }
        if (s2 != null && !s2.isEmpty()) {
            BindType t = BindType.fromString(s2);
            if (t != null) data.setSlot2(t);
        }

        if (cfg.isConfigurationSection("cooldowns")) {
            for (String key : cfg.getConfigurationSection("cooldowns").getKeys(false)) {
                long expiry = cfg.getLong("cooldowns." + key, 0L);
                if (expiry > System.currentTimeMillis()) {
                    data.getCooldowns().put(key, expiry);
                }
            }
        }
        return data;
    }

    public void save(UUID uuid) {
        PlayerData data = cache.get(uuid);
        if (data == null) return;
        File file = new File(dataFolder, uuid + ".yml");
        YamlConfiguration cfg = new YamlConfiguration();

        cfg.set("name", data.getPlayerName());
        cfg.set("slot1", data.getSlot1() != null ? data.getSlot1().name() : "");
        cfg.set("slot2", data.getSlot2() != null ? data.getSlot2().name() : "");

        for (Map.Entry<String, Long> entry : data.getCooldowns().entrySet()) {
            cfg.set("cooldowns." + entry.getKey(), entry.getValue());
        }

        try {
            cfg.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save data for " + uuid);
        }
    }

    public void saveAll() {
        for (UUID uuid : cache.keySet()) {
            save(uuid);
        }
    }

    public PlayerData get(Player player) {
        return cache.computeIfAbsent(player.getUniqueId(),
                k -> new PlayerData(player.getUniqueId(), player.getName()));
    }

    public PlayerData get(UUID uuid) {
        return cache.get(uuid);
    }

    public PlayerData loadPlayer(Player player) {
        PlayerData data = get(player);
        data.setPlayerName(player.getName());
        return data;
    }

    public Collection<PlayerData> getAllData() { return cache.values(); }
    public int getLoadedCount() { return cache.size(); }

    public int countPlayersWithBind(BindType type) {
        int count = 0;
        for (PlayerData d : cache.values()) {
            if (d.hasBind(type)) count++;
        }
        return count;
    }
}