package lv.cliquant.sellportal.Events;

import com.jeff_media.customblockdata.CustomBlockData;
import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Manager.GuiManager;
import lv.cliquant.sellportal.Manager.SellPortalManager;
import lv.cliquant.sellportal.Other.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class onPortalInteract implements Listener {

    public static NamespacedKey sellPortalKey;
    public static NamespacedKey sellPortalOwnerKey;
    public static NamespacedKey sellPortalNumberKey;
    public static NamespacedKey sellPortalXKey;
    public static NamespacedKey sellPortalYKey;
    public static NamespacedKey sellPortalZKey;

    public onPortalInteract() {
        sellPortalKey = new NamespacedKey(Main.getInstance(), "isSellPortal");
        sellPortalOwnerKey = new NamespacedKey(Main.getInstance(), "sellPortalOwner");
        sellPortalNumberKey = new NamespacedKey(Main.getInstance(), "sellPortalNumber");
        sellPortalXKey = new NamespacedKey(Main.getInstance(), "sellPortalX");
        sellPortalYKey = new NamespacedKey(Main.getInstance(), "sellPortalY");
        sellPortalZKey = new NamespacedKey(Main.getInstance(), "sellPortalZ");
    }

    @EventHandler
    public void onPortalInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block brokenBlock = event.getClickedBlock();
        if(brokenBlock == null) return;
        PersistentDataContainer container = new CustomBlockData(brokenBlock, Main.getInstance());
        if (brokenBlock.getType().equals(Material.BEDROCK)) {
            if (!(container.has(sellPortalKey, PersistentDataType.STRING))) {
                event.setCancelled(true);
                return;
            } else {
                if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
                    event.setCancelled(true);
                    return;
                }
                if (event.getHand().equals(Action.RIGHT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    return;
                }
            }

            if (!container.get(sellPortalOwnerKey, PersistentDataType.STRING).equals(player.getName())) {
                Utils.Color(Main.getInstance().getConfig().getString("messages.dont-own-this").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName()));
                event.setCancelled(true);
                return;
            }

            GuiManager.rightclickgui(player, container.get(sellPortalOwnerKey, PersistentDataType.STRING), container.get(sellPortalNumberKey, PersistentDataType.STRING), container.get(sellPortalXKey, PersistentDataType.STRING), container.get(sellPortalYKey, PersistentDataType.STRING), container.get(sellPortalZKey, PersistentDataType.STRING));
            player.openInventory(GuiManager.gui);
        }
    }
}
