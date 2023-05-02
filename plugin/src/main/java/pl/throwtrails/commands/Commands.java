package pl.throwtrails.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.throwtrails.ThrowTrails;

public class Commands implements CommandExecutor {

    private final ThrowTrails plugin = ThrowTrails.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("throwtrails.reload")) {
            if(args.length > 0) {
                if(args[0].equalsIgnoreCase("reload")) {
                    plugin.getDataHandler().reload();
                    sender.sendMessage("§aReloaded!");
                    return true;
                }
            }
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player!");
            return true;
        }
        plugin.getGUIHandler().openGUI((Player) sender);
        return true;
    }

}
