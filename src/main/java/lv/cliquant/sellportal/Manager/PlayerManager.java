package lv.cliquant.sellportal.Manager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {
    public static boolean hasAvaliableSlot(Player player){
        Inventory inv = player.getInventory();
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                return true;
            }
        }
        return false;
    }

    public static void updateBalance(String player, double amount) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(player);
        HookManager.econ.depositPlayer(target, amount);
    }
}
