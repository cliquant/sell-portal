package lv.cliquant.sellportal;

import eu.decentsoftware.holograms.api.DHAPI;
import lv.cliquant.sellportal.Commands.MainCommand;
import lv.cliquant.sellportal.Events.*;
import lv.cliquant.sellportal.Manager.*;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class Main extends JavaPlugin {

    public static Main instance;

    public static YamlConfiguration data;

    public static File file;
    public static FileConfiguration config;

    @Override
    public void onEnable() {

        instance = this;

        config = getConfig();

        saveDefaultConfig();

        file = new File(getInstance().getDataFolder(), "data.yml");
        data = new YamlConfiguration();

        if (!file.exists()) {
            Main.getInstance().getLogger().info("** Data file not found. Creating new one... **");
            Main.getInstance().saveResource("data.yml", false);
        }

        if(Main.getInstance().getConfig().getBoolean("update-checker")) {
            UpdateManager.check();
        }

        ItemManager.init();
        HookManager.load();
        SellPortalManager.init();

        if (Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("config")) {
            PriceManager.loadConfigPrices();
        }

        Main.getInstance().getCommand("sellportal").setExecutor(new MainCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new onBlockPlace(), Main.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new onEntitiyEnterPortal(), Main.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new onInventoryDrag(), Main.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new onInventoryClick(), Main.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerTeleport(), Main.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new onPortalInteract(), Main.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new onSellPortalBreak(), Main.getInstance());

        getLogger().info("*** Plugin has been loaded succesffully ***");
    }

    @Override
    public void onDisable() {
        if(HookManager.HologramHook == 1) {
            SellPortalManager.sellPortals1.forEach((key, value) -> {
                value.delete();
            });
            SellPortalManager.sellPortals2.clear();
        }
        if(HookManager.HologramHook == 2) {
            SellPortalManager.sellPortals2.forEach((key, value) -> {
                value.delete();
            });
            SellPortalManager.sellPortals2.clear();
        }
        SellPortalManager.soldSinceReboot.clear();
        getLogger().info("*** Unloading all portals ***");
        getLogger().info("*** Plugin has been unloaded succesffully ***");
    }

    public static Main getInstance() {
        return instance;
    }
}
