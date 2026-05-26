package com.bindsmp.altar;

import com.bindsmp.BindSMP;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AltarManager {
    private final BindSMP plugin;
    private final Map<Object, Object> rituals = new HashMap<>();

    public AltarManager(BindSMP plugin) { this.plugin = plugin; }
    public void load() {}
    public void saveAll() {}
    public void cancelAllRituals() {}
    public boolean isInRitual(Object uuid) { return false; }
    public Object getRitual(Object uuid) { return null; }
    public void interruptRitual(Object uuid, String reason) {}
    public void registerAltar(Object loc) {}
    public void unregisterAltar(Object loc) {}
    public boolean isAltar(Object loc) { return false; }
    public Object startRitual(Object player, Object type, Object loc) { return null; }
    public Map<Object, Object> getAllRituals() { return rituals; }
}