package pl.throwtrails.commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.throwtrails.ThrowTrails;
import pl.throwtrails.trails.Trail;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GUIHandler {

    private final ThrowTrails plugin = ThrowTrails.getInstance();
    private final List<Inventory> inventories = new ArrayList<>();

    public void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 45, "ThrowTrails");
        inventories.add(inventory);
        for(int i = 0; i < 45; i++) {
            inventory.setItem(i, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
        }
        List<Trail> playerAvailableTrails = getPlayerAvailableTrails(player);
        String playerPreference = plugin.getDataHandler().getPreferences().getOrDefault(player.getName(), null);
        if(playerAvailableTrails.size() > 0) {
            int i = 0;
            for(Trail t : playerAvailableTrails) {
                ItemStack is = t.getIcon();
                ItemMeta im = is.getItemMeta();
                if(im != null) {
                    im.setDisplayName("§e§l" + t.getID());
                    List<String> lore = new ArrayList<>();
                    if(t.getID().equals(playerPreference)) {
                        lore.add(" ");
                        lore.add("§aSELECTED!");
                    }
                    im.setLore(lore);
                    is.setItemMeta(im);
                }
                inventory.setItem(i, is);
                i++;
            }
        } else {
            ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("§4No trails");
            List<String> lore = new ArrayList<>();
            lore.add("§cYou don't have any trails!");
            im.setLore(lore);
            item.setItemMeta(im);
            inventory.setItem(22, item);
        }
        player.openInventory(inventory);
    }

    public boolean isRegisteredInventory(Inventory inventory) {
        return (getRegisteredInventory(inventory) != null);
    }

    @Nullable
    public Inventory getRegisteredInventory(Inventory inventory) {
        for(Inventory i : inventories) {
            if(i.equals(inventory)) return inventory;
        }
        return null;
    }

    public void removeInventory(Inventory inventory) {
        int i = -1;
        for(Inventory inv : inventories) {
            i++;
            if(inv.equals(inventory)) break;
        }
        if(i >= 0) inventories.remove(i);
    }

    public void click(Player player, int slot) {
        List<Trail> playerAvailableTrails = getPlayerAvailableTrails(player);
        int i = 0;
        for(Trail t : playerAvailableTrails) {
            if(i == slot) {
                plugin.getDataHandler().setPreference(player.getName(), t.getID());
                player.sendMessage("§aYou set trail to: " + t.getID());
                player.closeInventory();
                break;
            }
            i++;
        }
    }

    public List<Trail> getPlayerAvailableTrails(Player player) {
        List<Trail> playerAvailableTrails = new ArrayList<>();
        for(String id : plugin.getTrailsManager().getTrails().keySet()) {
            Trail t = plugin.getTrailsManager().getTrails().get(id);
            if(!player.hasPermission(t.getPermission())) continue;
            playerAvailableTrails.add(t);
        }
        return playerAvailableTrails;
    }

}
