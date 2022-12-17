package lv.cliquant.sellportal.Manager;

import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Other.Utils;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.util.HashMap;

public class SellPortalManager {

    public static HashMap<String, Hologram> sellPortals = new HashMap<>();

    public static HashMap<String, Double> soldSinceReboot = new HashMap<>();

    public static void init() {
        loadSellPortals();
    }

    public static void loadSellPortals() {
        try {
            Main.data.load(Main.file);
            Main.getInstance().getLogger().info("*** Loading SellPortals. ***");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConfigurationSection portalsSection = Main.data.getConfigurationSection("portals");
        if (portalsSection != null) {
            Main.getInstance().getLogger().info("*** Loading holograms. ***");
            for (String key : portalsSection.getKeys(false)) {
                ConfigurationSection portalSection = portalsSection.getConfigurationSection(key);
                if (portalSection != null) {
                    String owner = portalSection.getString("owner");
                    World world = Main.getInstance().getServer().getWorld(portalSection.getString("world"));

                    double x = portalSection.getDouble("location.x");
                    double y = portalSection.getDouble("location.y");
                    double z = portalSection.getDouble("location.z");

                    addPortalToHashMap(key);


                    Location loc = new Location(world, x,y,z).add(0.5, 3.0, 0.5);

                    Hologram hologram = HookManager.api.createHologram(loc);

                    for (String all : Main.config.getStringList("portal.hologram.lines")) {
                        hologram.getLines().appendText(ChatColor.translateAlternateColorCodes('&', all.replace("{owner}", owner)).replace("{totalsold}", soldSinceReboot.get(key) + ""));
                    }

                    sellPortals.put(key, hologram);
                }
            }
        }
    }

    public static void removePortal(String id, String owner, Integer x, Integer y, Integer z, World w) {
        Hologram hologram = sellPortals.get(id);
        hologram.delete();
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = z - 2; j <= z + 2; j++) {
                if (i == x - 2 || i == x + 2 || j == z - 2 || j == z + 2) {
                    Block block = w.getBlockAt(i, y, j);

                    block.setType(Material.AIR);
                } else {
                    Block block = w.getBlockAt(i, y, j);
                    block.setType(Material.AIR);
                }
            }
        }
        soldSinceReboot.remove(id);
        sellPortals.remove(id);
        Main.getInstance().data.set("portals." + id, null);
        try {
            Main.getInstance().data.save(Main.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getPlayer(owner).getInventory().addItem(ItemManager.getSellPortalItem().clone());
        Bukkit.getPlayer(owner).sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.portal-mined").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
    }

    public static void addPortalToHashMap(String uuid) {
        soldSinceReboot.put(uuid, 0.0);
    }

    public static void addMoneyToPortalBalance(String uuid, String owner, Double money) {
        double oldbalance = soldSinceReboot.get(uuid);
        soldSinceReboot.put(uuid, oldbalance + money);

        Hologram hologram = sellPortals.get(uuid);

        Integer linecount = 0;

        hologram.getLines().clear();

        for (String all : Main.config.getStringList("portal.hologram.lines")) {
            hologram.getLines().appendText(ChatColor.translateAlternateColorCodes('&', all.replace("{owner}", owner)).replace("{totalsold}", soldSinceReboot.get(uuid) + ""));
        }
        Bukkit.broadcastMessage("test4");
    }

    public static void reloadPortals() {
        sellPortals.clear();
        soldSinceReboot.clear();
        HookManager.api.deleteHolograms();
        init();
        Main.getInstance().getLogger().info("*** Unloading all portals ***");
        Main.getInstance().getLogger().info("*** Loading all portals ***");
        Main.getInstance().getLogger().info("*** Portals has been reloaded succesffully ***");
    }

    public static void givePortal(String playername) {
        Player player = Bukkit.getPlayer(playername);
        player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.you-got-sellportal").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
        player.getInventory().addItem(ItemManager.getSellPortalItem());
    }
}
