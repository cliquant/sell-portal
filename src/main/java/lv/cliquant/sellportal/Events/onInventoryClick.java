package lv.cliquant.sellportal.Events;

import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Manager.GuiManager;
import lv.cliquant.sellportal.Manager.SellPortalManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class onInventoryClick implements Listener {

    public static String owner;
    public static String key;
    public static Integer x;
    public static Integer y;
    public static Integer z;

    public static World w;

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if(event.getInventory().equals(GuiManager.gui)) {
                if(event.getSlot() == Main.getInstance().getConfig().getInt("portal.guis.rightclick.items.removeportal.slot")) {
                    SellPortalManager.removePortal(key, owner, x, y, z, w);
                    event.getWhoClicked().closeInventory();
                }
                event.setCancelled(true);
        }
    }
}
