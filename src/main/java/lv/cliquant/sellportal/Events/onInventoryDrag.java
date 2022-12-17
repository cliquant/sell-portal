package lv.cliquant.sellportal.Events;

import lv.cliquant.sellportal.Manager.GuiManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class onInventoryDrag implements Listener {
    @EventHandler
    public void inventoryDragEvent(InventoryMoveItemEvent event) {
        if (event.getDestination().equals(GuiManager.gui)) {
            event.setCancelled(true);
        }
    }
}
