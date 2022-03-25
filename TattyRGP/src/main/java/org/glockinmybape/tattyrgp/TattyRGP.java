package org.glockinmybape.tattyrgp;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TattyRGP extends JavaPlugin implements Listener {
    private List<String> fregions;
    private List<String> forbcommands;
    private String message;

    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("§bTattyBlocker | Enabling...");
        this.getServer().getConsoleSender().sendMessage("§bTattyBlocker | Loading config and Events...");
        this.forbcommands = Arrays.asList("rem", "remove", "delete", "del");
        this.saveDefaultConfig();
        this.fregions = this.getConfig().getStringList("tregions");
        this.message = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("message"));
        this.getServer().getPluginManager().registerEvents(this, this);

        Logger log = Bukkit.getLogger();
        log.info("§b");
        log.info("§b .----------------------------------------------------------. ");
        log.info("§b| .-------------------------------------------------------. |");
        log.info("§b| |             \t\t\t\t\t\t");
        log.info("§b| |            §7Плагин: §bTattyRGP§8| §7Версия: §b1.0                ");
        log.info("§b| |        §7Создан для §bTattyWorld §8- §7Разработал: §bglockinmybape\t");
        log.info("§b| |                    §bvk.com/TattyWorld");
        log.info("§b| |             \t\t\t\t\t\t");
        log.info("§b| '-------------------------------------------------------'§b|");
        log.info("§b'-----------------------------------------------------------'");
        log.info("§b");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tblocker") && sender.hasPermission("tattyblocker.admin")) {
            if (args.length < 2) {
                sender.sendMessage("");
                sender.sendMessage("§b§lTattyRGP");
                sender.sendMessage("");
                sender.sendMessage("§b/tblocker add [region] - Добавить регион");
                sender.sendMessage("§b/tblocker remove [region] - Удалить регион");
                sender.sendMessage("");
                return false;
            } else if (args[0].equalsIgnoreCase("add")) {
                if (args[1] != null && !args[1].isEmpty()) {
                    if (this.fregions.contains(args[1])) {
                        sender.sendMessage("§cТакой регион уже в списке");
                        return false;
                    } else {
                        this.fregions.add(args[1]);
                        this.getConfig().set("fregions", this.fregions);
                        this.saveConfig();
                        sender.sendMessage("§aРегион " + args[1] + " добавлен в список.");
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args[1] != null && !args[1].isEmpty()) {
                    if (!this.fregions.contains(args[1])) {
                        sender.sendMessage("§cТакой регион не в списке");
                        return false;
                    } else {
                        this.fregions.removeIf((rg) -> {
                            return rg.equals(args[1]);
                        });
                        this.getConfig().set("tregions", this.fregions);
                        this.saveConfig();
                        sender.sendMessage("§aРегион " + args[1] + " удалён из списка.");
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @EventHandler
    public void onCommands(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();
        if (!this.fregions.isEmpty() && message != null && !message.isEmpty()) {
            String[] args = message.split(" ");
            if (args.length >= 3 && (args[0].equalsIgnoreCase("/rg") || args[0].equalsIgnoreCase("/region")) && this.forbcommands.contains(args[1])) {
                if (this.fregions.contains(args[2])) {
                    e.getPlayer().sendMessage(this.message.replace("%rg%", args[2]));
                    e.setCancelled(true);
                }
            }
        }
    }
}
