package lv.cliquant.sellportal.Manager;

import lv.cliquant.sellportal.Main;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class HookManager {

    public static Economy econ;

    public static HolographicDisplaysAPI api;
    public static void load() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            Main.getInstance().getLogger().info("*** Hooked in to Vault ***");
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            econ = rsp.getProvider();
        }
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("HolographicDisplays")) {
            Main.getInstance().getLogger().info("*** Hooked in to HolographicDisplays ***");
            api = HolographicDisplaysAPI.get(Main.getInstance());
        }
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("ShopGuiPlus")) {
            if (Main.getInstance().getConfig().getBoolean("sell.hooks.shopguiplus")) {
                Main.getInstance().getLogger().info("*** Hooked in to ShopGuiPLus ***");
            }
        }
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Main.getInstance().getLogger().info("*** Hooked in to PlaceholderAPI ***");
        }

        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            Main.getInstance().getLogger().info(" ");
            Main.getInstance().getLogger().info("*** Vault is not installed or not enabled. ***");
            Main.getInstance().getLogger().info("*** This plugin will be disabled. ***");
            Main.getInstance().getLogger().info(" ");
            Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
            return;
        }
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("HolographicDisplays")) {
            Main.getInstance().getLogger().info(" ");
            Main.getInstance().getLogger().info("*** HolographicDisplays is not installed or not enabled. ***");
            Main.getInstance().getLogger().info("*** This plugin will be disabled. ***");
            Main.getInstance().getLogger().info(" ");
            Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
            return;
        }
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("ShopGuiPlus")) {
            if (Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("shopguiplus")) {
                Main.getInstance().getLogger().info(" ");
                Main.getInstance().getLogger().info("*** ShopGuiPlus is not installed or not enabled. ***");
                Main.getInstance().getLogger().info("*** This plugin will be disabled. ***");
                Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
                return;
            }
        }
    }
}
