package lv.cliquant.sellportal.Manager;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IEssentials;
import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Other.Utils;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PriceManager {

    public static HashMap<String, Double> configPrices = new HashMap<>();

    public static void loadConfigPrices() {
        for (String all : Main.getInstance().getConfig().getStringList("sell.prices")) {
            String material = all.split(":")[0];
            Double price = Double.parseDouble(all.split(":")[1]);
            configPrices.put(material, price);
        }
    }

    public static double getConfigPrice(ItemStack item, Integer count) {
        if(configPrices.get(item.getType().name()) != null || configPrices.containsKey(item.getType().name())) {
            return configPrices.get(item.getType().name()) * count;
        } else {
            return 0.0;
        }
    }

    public static double getItemPrice(ItemStack item) {
        if (Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("shopguiplus")) {
            double price = ShopGuiPlusApi.getItemStackPriceSell(item);
            if (price == -1.0) {
                return 0.0;
            } else {
                return price;
            }
        } else if(Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("essentials")) {
            IEssentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
            if(essentials.getWorth().getPrice(essentials, item) == null) {
                return 0.0;
            } else {
                return Double.parseDouble(String.valueOf(essentials.getWorth().getPrice(essentials, item))) * item.getAmount();
            }
        } else {
            return getConfigPrice(item, item.getAmount());
        }
    }
}
