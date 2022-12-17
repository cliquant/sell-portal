package lv.cliquant.sellportal.Events;

import com.jeff_media.customblockdata.CustomBlockData;
import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Manager.PlayerManager;
import lv.cliquant.sellportal.Manager.PriceManager;
import lv.cliquant.sellportal.Manager.SellPortalManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class onEntitiyEnterPortal implements Listener {

    public static NamespacedKey sellPortalKey;
    public static NamespacedKey sellPortalOwnerKey;
    public static NamespacedKey sellPortalNumberKey;
    public static NamespacedKey sellPortalXKey;
    public static NamespacedKey sellPortalYKey;
    public static NamespacedKey sellPortalZKey;
    public onEntitiyEnterPortal() {
        sellPortalKey = new NamespacedKey(Main.getInstance(), "isSellPortal");
        sellPortalOwnerKey = new NamespacedKey(Main.getInstance(), "sellPortalOwner");
        sellPortalNumberKey = new NamespacedKey(Main.getInstance(), "sellPortalNumber");
        sellPortalXKey = new NamespacedKey(Main.getInstance(), "sellPortalX");
        sellPortalYKey = new NamespacedKey(Main.getInstance(), "sellPortalY");
        sellPortalZKey = new NamespacedKey(Main.getInstance(), "sellPortalZ");
    }

    @EventHandler
    public void entitiyEnterPortal(EntityPortalEvent event) {
        Location loc = event.getEntity().getLocation();
        Block block = loc.getBlock();
        PersistentDataContainer container = new CustomBlockData(block, Main.getInstance());

        if(!(block.getType().toString().equals("END_PORTAL"))) return;
        if(!(event.getEntityType().toString().equalsIgnoreCase("dropped_item"))) {
            event.setCancelled(true);
            return;
        }
        if(!container.has(sellPortalKey, PersistentDataType.STRING) && !container.has(sellPortalOwnerKey, PersistentDataType.STRING) && !container.has(sellPortalNumberKey, PersistentDataType.STRING)) return;
        if(event.getEntity() instanceof Player) {
            return;
        }

        Item item = (Item) event.getEntity();
        ItemStack itemStack = item.getItemStack();
        if(PriceManager.getItemPrice(itemStack) == 0.0) {
            Bukkit.broadcastMessage("test1");
            Bukkit.getPlayer(item.getThrower()).getInventory().addItem(itemStack);
            item.remove();
        }
        if(PriceManager.getItemPrice(itemStack) != 0.0) {
            Bukkit.broadcastMessage("test2");
            item.remove();
            PlayerManager.updateBalance(container.get(sellPortalOwnerKey, PersistentDataType.STRING), PriceManager.getItemPrice(itemStack));
            SellPortalManager.addMoneyToPortalBalance(container.get(sellPortalNumberKey, PersistentDataType.STRING), container.get(sellPortalOwnerKey, PersistentDataType.STRING), PriceManager.getItemPrice(itemStack));
        }
        event.setCancelled(true);
    }
}
