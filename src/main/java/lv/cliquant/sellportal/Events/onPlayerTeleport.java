package lv.cliquant.sellportal.Events;

import com.jeff_media.customblockdata.CustomBlockData;
import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Manager.SellPortalManager;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class onPlayerTeleport implements Listener {

    public static NamespacedKey sellPortalKey;
    public static NamespacedKey sellPortalOwnerKey;
    public static NamespacedKey sellPortalNumberKey;
    public static NamespacedKey sellPortalXKey;
    public static NamespacedKey sellPortalYKey;
    public static NamespacedKey sellPortalZKey;

    public onPlayerTeleport() {
        sellPortalKey = new NamespacedKey(Main.getInstance(), "isSellPortal");
        sellPortalOwnerKey = new NamespacedKey(Main.getInstance(), "sellPortalOwner");
        sellPortalNumberKey = new NamespacedKey(Main.getInstance(), "sellPortalNumber");
        sellPortalXKey = new NamespacedKey(Main.getInstance(), "sellPortalX");
        sellPortalYKey = new NamespacedKey(Main.getInstance(), "sellPortalY");
        sellPortalZKey = new NamespacedKey(Main.getInstance(), "sellPortalZ");
    }

    @EventHandler
    public void playerTeleport(PlayerTeleportEvent event) {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            Block block = event.getPlayer().getLocation().getBlock();
            PersistentDataContainer container = new CustomBlockData(block, Main.getInstance());
            if(container.has(sellPortalKey, PersistentDataType.STRING)) {
                event.setCancelled(true);
            }
        }
    }
}
