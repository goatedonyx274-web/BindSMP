package com.bindsmp.bind;

import org.bukkit.ChatColor;

public enum BindType {

    SOUL    ("Soul Bind",   "&5",  ChatColor.DARK_PURPLE,  "Lifesteal, HP linking, drain ultimate"),
    CHAIN   ("Chain Bind",  "&7",  ChatColor.GRAY,         "Pulls enemies, chaining & dragging"),
    BLOOD   ("Blood Bind",  "&c",  ChatColor.RED,          "Low-HP power, sacrifice damage"),
    OATH    ("Oath Bind",   "&6",  ChatColor.GOLD,         "Ally protection, trust-based power"),
    SHADOW  ("Shadow Bind", "&8",  ChatColor.DARK_GRAY,    "Darkness speed, blink behind targets"),
    FLAME   ("Flame Bind",  "&c",  ChatColor.RED,          "Fire immunity, burning aura, inferno"),
    FROST   ("Frost Bind",  "&b",  ChatColor.AQUA,         "Slows enemies, freezes areas"),
    STORM   ("Storm Bind",  "&e",  ChatColor.YELLOW,       "Lightning strikes, rain empowered"),
    VOID    ("Void Bind",   "&3",  ChatColor.DARK_AQUA,    "Gravity manipulation, pull effects"),
    STONE   ("Stone Bind",  "&f",  ChatColor.WHITE,        "Defense, damage reflection, earthquake");

    private final String displayName;
    private final String colorCode;
    private final ChatColor chatColor;
    private final String shortDesc;

    BindType(String displayName, String colorCode, ChatColor chatColor, String shortDesc) {
        this.displayName = displayName;
        this.colorCode = colorCode;
        this.chatColor = chatColor;
        this.shortDesc = shortDesc;
    }

    public String getDisplayName() { return displayName; }
    public String getColorCode() { return colorCode; }
    public ChatColor getChatColor() { return chatColor; }
    public String getShortDesc() { return shortDesc; }

    public String getColored() {
        return com.bindsmp.util.MessageUtil.color(colorCode + "&l" + displayName);
    }

    public static BindType fromString(String s) {
        for (BindType t : values()) {
            if (t.name().equalsIgnoreCase(s) || t.displayName.equalsIgnoreCase(s)) return t;
        }
        return null;
    }
}