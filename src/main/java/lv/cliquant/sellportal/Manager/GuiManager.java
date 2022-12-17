package lv.cliquant.sellportal.Manager;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lv.cliquant.sellportal.Events.onInventoryClick;
import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GuiManager {

    public static Inventory gui;

    public static void rightclickgui(Player opener, String owner, String portalid, String portalx, String portaly, String portalz) {
        Player player = opener;
        gui = Bukkit.createInventory(player, Main.getInstance().getConfig().getInt("portal.guis.rightclick.size"), Utils.Color(Main.getInstance().getConfig().getString("portal.guis.rightclick.title")));
        for (int x = 0; x < gui.getSize(); x++) {
            if(Main.getInstance().getConfig().getString("portal.guis.rightclick.filler").equals("NONE")) {

            } else {
                ItemStack filler = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("portal.guis.rightclick.filler"))); //Gives the player a weapon

                gui.setItem(x, filler);
            }
        }
        ConfigurationSection portalsSection = Main.getInstance().getConfig().getConfigurationSection("portal.guis.rightclick.items");
        if (portalsSection != null) {
            for (String key : portalsSection.getKeys(false)) {
                ConfigurationSection portalSection = portalsSection.getConfigurationSection(key);
                if (portalSection != null) {
                    ItemStack item;
                    ItemMeta item_meta;

                    if (portalSection.getString("material").contains("head-")) {
                        boolean isNewVersion = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");

                        String headid = portalSection.getString("material").split("-")[1];
                        item = new ItemStack(Material.valueOf(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM")); //Gives the player a weapon
                        item_meta = item.getItemMeta();
                        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

                        profile.getProperties().put("textures", new Property("textures", headid));

                        Field profileField = null;
                        try {
                            profileField = item_meta.getClass().getDeclaredField("profile");
                        } catch (NoSuchFieldException | SecurityException e) {
                            e.printStackTrace();
                        }
                        assert profileField != null;
                        profileField.setAccessible(true);
                        try {
                            profileField.set(item_meta, profile);
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        item = new ItemStack(Material.valueOf(portalSection.getString("material")));
                        item_meta = item.getItemMeta();
                    }

                    item_meta.setDisplayName(Utils.Color(portalSection.getString("title")));

                    List<String> lore = new ArrayList<>();

                    for (String all : portalSection.getStringList("lore")) {
                        lore.add(Utils.Color(all).replace("{owner}", owner).replace("{totalsold}", SellPortalManager.soldSinceReboot.get(portalid) + ""));
                    }
                    item_meta.setLore(lore);
                    item.setItemMeta(item_meta);

                    gui.setItem(portalSection.getInt("slot"), item);
                    onInventoryClick.owner = owner;
                    onInventoryClick.key = portalid;
                    onInventoryClick.x = Integer.parseInt(portalx);
                    onInventoryClick.y = Integer.parseInt(portaly);
                    onInventoryClick.z = Integer.parseInt(portalz);
                    onInventoryClick.w = opener.getWorld();
                }
            }
        }
    }
}