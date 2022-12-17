package lv.cliquant.sellportal.Commands;

import lv.cliquant.license.License;
import lv.cliquant.sellportal.Main;
import lv.cliquant.sellportal.Manager.*;
import lv.cliquant.sellportal.Other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if(args.length == 0) {
                sender.sendMessage(Utils.Color("{prefix} &7License owner: " + License.userID + ", " + License.userName).replace("{prefix}", Utils.Color(Main.getInstance().getConfig().getString("messages.prefix"))));
                sender.sendMessage(Utils.Color("{prefix} &7/sellportal give (player) - gives player sellportal".replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
                sender.sendMessage(Utils.Color("{prefix} &7/sellportal reload (config/portals/all) - reloads conifg/portals".replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
            }

            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args[1]);

                    if(target == null) {
                        sender.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.player-is-offline").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", args[1])));
                        return true;
                    }

                    if(!(PlayerManager.hasAvaliableSlot(target))) {
                        sender.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.player-inventory-full").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", target.getName())));;
                        return true;
                    }

                    SellPortalManager.givePortal(args[1]);
                    sender.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.gave-sellportal").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", target.getName())));
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload")) {
                    if(args[1].equalsIgnoreCase("config")) {
                        Main.getInstance().reloadConfig();
                        HookManager.load();
                        Main.getInstance().getLogger().info("*** Config has been reloaded succesffully ***");
                        sender.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.config-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
                        ItemManager.init();
                        HookManager.load();
                        if (Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("config")) {
                            PriceManager.loadConfigPrices();
                        }
                        return true;
                    }
                    if(args[1].equalsIgnoreCase("portals")) {
                        SellPortalManager.reloadPortals();
                        sender.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.portals-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));

                        return true;
                    }
                    if(args[1].equalsIgnoreCase("all")) {
                        Main.getInstance().reloadConfig();
                        HookManager.load();
                        Main.getInstance().getLogger().info("*** Config has been reloaded succesffully ***");
                        sender.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.config-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
                        SellPortalManager.reloadPortals();
                        sender.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.portals-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
                        ItemManager.init();
                        HookManager.load();

                        if (Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("config")) {
                            PriceManager.loadConfigPrices();
                        }
                        return true;
                    }
                }
            }
            return true;
        }
        Player player = (Player) sender;

        if(!(player.hasPermission("sellportal.command"))) {
            player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.no-permission").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
        }

        if(args.length == 0) {
            player.sendMessage(Utils.Color("{prefix} &7License owner: " + License.userID + ", " + License.userName).replace("{prefix}", Utils.Color(Main.getInstance().getConfig().getString("messages.prefix"))));
            player.sendMessage(Utils.Color("{prefix} &7/sellportal give (player) - gives player sellportal".replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
            player.sendMessage(Utils.Color("{prefix} &7/sellportal reload (config/portals/all) - reloads conifg/portals".replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
        }

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("give")) {
                if(!(player.hasPermission("sellportal.give"))) {
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.no-permission").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);

                if(target == null) {
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.player-is-offline").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", args[1])));
                    return true;
                }

                if(!(PlayerManager.hasAvaliableSlot(target))) {
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.player-inventory-full").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));;
                    return true;
                }

                SellPortalManager.givePortal(args[1]);
                player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.gave-sellportal").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", target.getName())));
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")) {
                if(!(player.hasPermission("sellportal.reload"))) {
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.no-permission").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix"))));
                    return true;
                }
                if(args[1].equalsIgnoreCase("config")) {
                    Main.getInstance().reloadConfig();
                    HookManager.load();
                    Main.getInstance().getLogger().info("*** Config has been reloaded succesffully ***");
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.config-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
                    ItemManager.init();
                    HookManager.load();
                    if (Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("config")) {
                        PriceManager.loadConfigPrices();
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("portals")) {
                    SellPortalManager.reloadPortals();
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.portals-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
                    return true;
                }
                if(args[1].equalsIgnoreCase("all")) {
                    Main.getInstance().reloadConfig();
                    HookManager.load();
                    Main.getInstance().getLogger().info("*** Config has been reloaded succesffully ***");
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.config-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
                    SellPortalManager.reloadPortals();
                    player.sendMessage(Utils.Color(Main.getInstance().getConfig().getString("messages.portals-reloaded").replace("{prefix}", Main.getInstance().getConfig().getString("messages.prefix")).replace("{player}", player.getName())));
                    ItemManager.init();
                    HookManager.load();
                    if (Main.getInstance().getConfig().getString("sell.pricesource").equalsIgnoreCase("config")) {
                        PriceManager.loadConfigPrices();
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
