package lv.cliquant.sellportal.Events;

import com.jeff_media.customblockdata.CustomBlockData;
import lv.cliquant.sellportal.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class onSellPortalBreak implements Listener {

    public static NamespacedKey sellPortalKey;
    public static NamespacedKey sellPortalOwnerKey;
    public static NamespacedKey sellPortalNumberKey;
    public static NamespacedKey sellPortalXKey;
    public static NamespacedKey sellPortalYKey;
    public static NamespacedKey sellPortalZKey;

    public onSellPortalBreak() {
        sellPortalKey = new NamespacedKey(Main.getInstance(), "isSellPortal");
        sellPortalOwnerKey = new NamespacedKey(Main.getInstance(), "sellPortalOwner");
        sellPortalNumberKey = new NamespacedKey(Main.getInstance(), "sellPortalNumber");
        sellPortalXKey = new NamespacedKey(Main.getInstance(), "sellPortalX");
        sellPortalYKey = new NamespacedKey(Main.getInstance(), "sellPortalY");
        sellPortalZKey = new NamespacedKey(Main.getInstance(), "sellPortalZ");
    }

    @EventHandler
    public void onSellPortalBreak(BlockBreakEvent event) {
        Block brokenBlock = event.getBlock();

        PersistentDataContainer container = new CustomBlockData(brokenBlock, Main.getInstance());
        if(container == null) return;
        if (container.has(sellPortalKey, PersistentDataType.STRING)) {
            event.setCancelled(true);
        }
    }
}
