package com.bindsmp.trust;

import com.bindsmp.BindSMP;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TrustManager {
    private final BindSMP plugin;
    private final Map<UUID, Set<String>> trustMap = new HashMap<>();

    public TrustManager(BindSMP plugin) { this.plugin = plugin; }
    public void load() {}
    public void saveAll() {}
    public boolean trusts(UUID player, String target) { return false; }
    public Set<String> getTrustedByName(Object player) { return new HashSet<>(); }
}