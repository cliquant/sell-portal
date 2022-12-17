package lv.cliquant.sellportal.Manager;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lv.cliquant.sellportal.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemManager {

    private static ItemStack sellPortalItem;
    private static FileConfiguration config;

    public static void init() {
        config = Main.getInstance().getConfig();

        if(Main.getInstance().getConfig().getString("portal.item.material").contains("head-")) {
            createSellPortalHead();
        } else {
            createSellPortalItem();
        }
    }

    public static void createSellPortalItem() {

        List<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("portal.item.material")), 1);
        ItemMeta meta = item.getItemMeta();
        String displayName = ChatColor.translateAlternateColorCodes('&', config.getString("portal.item.title"));


        for (String all : config.getStringList("portal.item.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all));
        }

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTItem nbt = new NBTItem(item);
        nbt.setBoolean("isSellPortal", true);
        nbt.applyNBT(item);

        sellPortalItem = nbt.getItem();
    }

    public static void createSellPortalHead() {

        boolean isNewVersion = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");

        String headid = Main.getInstance().getConfig().getString("portal.item.material").split("-")[1];

        ItemStack item = new ItemStack(Material.valueOf(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM"), 1);
        ItemMeta meta = item.getItemMeta();
        String displayName = ChatColor.translateAlternateColorCodes('&', config.getString("portal.item.title"));

        GameProfile profile = new GameProfile(UUID.fromString("6f42e0ce-7cb5-11ed-a1eb-0242ac120002"), null);

        profile.getProperties().put("textures", new Property("textures", headid));

        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        assert profileField != null;
        profileField.setAccessible(true);
        try {
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        List<String> lore = new ArrayList<>();

        for (String all : config.getStringList("portal.item.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all));
        }

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTItem nbt = new NBTItem(item);
        nbt.setBoolean("isSellPortal", true);
        nbt.applyNBT(item);

        sellPortalItem = nbt.getItem();
    }

    public static ItemStack getSellPortalItem() {
        return sellPortalItem;
    }
}
