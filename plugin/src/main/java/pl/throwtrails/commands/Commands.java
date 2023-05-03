package pl.throwtrails.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.throwtrails.ThrowTrails;
import pl.throwtrails.data.DataHandler;

public class Commands implements CommandExecutor {

    private final ThrowTrails plugin = ThrowTrails.getInstance();
    private final DataHandler dataHandler = plugin.getDataHandler();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("throwtrails.reload")) {
            if(args.length > 0) {
                if(args[0].equalsIgnoreCase("reload")) {
                    plugin.getDataHandler().reload();
                    sender.sendMessage(dataHandler.getMessage("reload"));
                    return true;
                }
            }
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(dataHandler.getMessage("mustBePlayer"));
            return true;
        }
        new GUIHandler((Player) sender);
        return true;
    }

}
