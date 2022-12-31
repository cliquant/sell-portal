package lv.cliquant.sellportal.Events;

import com.jeff_media.customblockdata.CustomBlockData;
import de.tr7zw.changeme.nbtapi.NBTItem;
import eu.decentsoftware.holograms.api.DHAPI;
import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Manager.HookManager;
import lv.cliquant.sellportal.Manager.ItemManager;
import lv.cliquant.sellportal.Manager.SellPortalManager;
import lv.cliquant.sellportal.Other.Utils;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class onBlockPlace implements Listener {

    public static NamespacedKey sellPortalKey;
    public static NamespacedKey sellPortalOwnerKey;
    public static NamespacedKey sellPortalNumberKey;
    public static NamespacedKey sellPortalXKey;
    public static NamespacedKey sellPortalYKey;
    public static NamespacedKey sellPortalZKey;

    public onBlockPlace() {

        sellPortalKey = new NamespacedKey(Main.getInstance(), "isSellPortal");
        sellPortalOwnerKey = new NamespacedKey(Main.getInstance(), "sellPortalOwner");
        sellPortalNumberKey = new NamespacedKey(Main.getInstance(), "sellPortalNumber");
        sellPortalXKey = new NamespacedKey(Main.getInstance(), "sellPortalX");
        sellPortalYKey = new NamespacedKey(Main.getInstance(), "sellPortalY");
        sellPortalZKey = new NamespacedKey(Main.getInstance(), "sellPortalZ");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();
        NBTItem nbt = new NBTItem(itemInHand);
        if (!nbt.hasKey("isSellPortal") && !nbt.getBoolean("isSellPortal")) return;
        Player player = event.getPlayer();
        Block placedBlock = event.getBlockPlaced();
        if (!player.hasPermission("sellportal.place"))
        if(player.getLocation().distance(placedBlock.getLocation()) < 3) {
            player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.take-distance").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
            event.setCancelled(true);
            return;
        }

        int x = placedBlock.getX();
        int y = placedBlock.getY();
        int z = placedBlock.getZ();

        World w = placedBlock.getWorld();

        UUID randomUUID = UUID.randomUUID();

        String key = randomUUID.toString();

        boolean canMakePortal = true;
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = z - 2; j <= z + 2; j++) {
                placedBlock.setType(Material.AIR);
                if (!(w.getBlockAt(i, y, j).getType().equals(Material.AIR))) {
                    canMakePortal = false;
                }
            }
        }

        if (canMakePortal) {
            for (int i = x - 2; i <= x + 2; i++) {
                for (int j = z - 2; j <= z + 2; j++) {
                    if (i == x - 2 || i == x + 2 || j == z - 2 || j == z + 2) {
                        Block block = w.getBlockAt(i, y, j);

                        PersistentDataContainer container = new CustomBlockData(block, Main.getInstance());

                        container.set(sellPortalKey, PersistentDataType.STRING, "true");
                        container.set(sellPortalOwnerKey, PersistentDataType.STRING, player.getName());
                        container.set(sellPortalNumberKey, PersistentDataType.STRING, key);
                        container.set(sellPortalXKey, PersistentDataType.STRING, x + "");
                        container.set(sellPortalYKey, PersistentDataType.STRING, y + "");
                        container.set(sellPortalZKey, PersistentDataType.STRING, z + "");

                        block.setType(Material.BEDROCK);
                    } else {
                        Block block = w.getBlockAt(i, y, j);

                        PersistentDataContainer container = new CustomBlockData(block, Main.getInstance());

                        container.set(sellPortalKey, PersistentDataType.STRING, "true");
                        container.set(sellPortalOwnerKey, PersistentDataType.STRING, player.getName());
                        container.set(sellPortalNumberKey, PersistentDataType.STRING, key);
                        container.set(sellPortalXKey, PersistentDataType.STRING, x + "");
                        container.set(sellPortalYKey, PersistentDataType.STRING, y + "");
                        container.set(sellPortalZKey, PersistentDataType.STRING, z + "");

                        block.setType(Material.END_PORTAL);
                    }
                }
            }
        } else {
            player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.need-space").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
            event.setCancelled(true);
            return;
        }

        SellPortalManager.addPortalToHashMap(key);

        double x1 = placedBlock.getLocation().getX();
        double y1 = placedBlock.getLocation().getY();
        double z1 = placedBlock.getLocation().getZ();

        if(HookManager.HologramHook == 1) {
            Location loc = event.getBlockPlaced().getLocation().add( 0.5, 3.0, 0.5);

            Hologram hologram = HolographicDisplaysAPI.get(Main.getInstance()).createHologram(loc);

            for (String all : Main.config.getStringList("portal.hologram.lines")) {
                hologram.getLines().appendText(org.bukkit.ChatColor.translateAlternateColorCodes('&', all.replace("{owner}", player.getName())).replace("{totalsold}", SellPortalManager.soldSinceReboot.get(key) + ""));
            }

            SellPortalManager.sellPortals1.put(key, hologram);
        }
        if(HookManager.HologramHook == 2) {
            if(DHAPI.getHologram(key) != null) {
                DHAPI.getHologram(key).delete();
            }

            Location loc = event.getBlockPlaced().getLocation().add( 0.5, 3.0, 0.5);

            List<String> lines = new ArrayList<>();

            for (String all : Main.config.getStringList("portal.hologram.lines")) {
                lines.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', all.replace("{owner}", player.getName())).replace("{totalsold}", SellPortalManager.soldSinceReboot.get(key) + ""));
            }

            eu.decentsoftware.holograms.api.holograms.Hologram hologram = DHAPI.createHologram(key, loc, lines);

            SellPortalManager.sellPortals2.put(key, hologram);
        }

        Main.data.set("portals." + key + ".owner", player.getName());
        Main.data.set("portals." + key + ".world", w.getName());
        Main.data.set("portals." + key + ".location.x", x1);
        Main.data.set("portals." + key + ".location.y", y1);
        Main.data.set("portals." + key + ".location.z", z1);

        try {
            Main.getInstance().data.save(Main.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.placed-sellportal").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
    }
}
