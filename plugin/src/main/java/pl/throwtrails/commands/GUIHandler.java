package pl.throwtrails.commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.throwtrails.ThrowTrails;
import pl.throwtrails.data.DataHandler;
import pl.throwtrails.trails.Trail;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class GUIHandler {

    private final ThrowTrails plugin = ThrowTrails.getInstance();
    private final DataHandler dataHandler = plugin.getDataHandler();
    private final Player player;
    private final Inventory inventory;
    private int offset;
    private final List<Trail> playerAvailableTrails;

    public GUIHandler(Player player) {
        player.closeInventory();
        this.player = player;
        this.playerAvailableTrails = getPlayerAvailableTrails();
        this.inventory = Bukkit.createInventory(player, 45, dataHandler.getMessage("title"));
        player.openInventory(inventory);
        openGUI(0);
        plugin.getGUIManager().getGUIs().add(this);
    }

    public void openGUI(int offset) {
        this.offset = offset;
        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
        for(int i = 0; i < 45; i++) {
            addItem(i, Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", null);
        }
        String playerPreference = plugin.getDataHandler().getPreferences().getOrDefault(player.getName(), null);
        if(playerAvailableTrails.size() > 0) {
            int i = 0;
            int offsetCount = 0;
            for(Trail t : playerAvailableTrails) {
                if(offsetCount < offset) {
                    offsetCount++;
                    continue;
                }
                if(i >= 36) break;
                if(t.getID().equals(playerPreference)) {
                    addItem(i, t.getIcon(), "§e§l" + t.getID(), dataHandler.getMessage("selected"));
                } else {
                    addItem(i, t.getIcon(), "§e§l" + t.getID(), dataHandler.getMessage("clickToSelect"));
                }
                i++;
            }
            if(playerAvailableTrails.size() > offset + 36) {
                addItem(44, Material.ARROW, dataHandler.getMessage("nextPage"), null);
            }
            if(offset >= 36) {
                addItem(36, Material.ARROW, dataHandler.getMessage("previousPage"), null);
            }
            addItem(40, Material.BARRIER, dataHandler.getMessage("clearTrails"),
                    dataHandler.getMessage("clearTrailsDescription"));
        } else {
            addItem(22, Material.RED_STAINED_GLASS_PANE, dataHandler.getMessage("noTrails"),
                    dataHandler.getMessage("noTrailsDescription"));
        }
    }

    public void addItem(int slot, Material material, String name, String lore) {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        if(im != null) {
            if(name != null) im.setDisplayName(name);
            if(lore != null) {
                String[] splitLore = lore.split("%nl%");
                List<String> loreList = Arrays.asList(splitLore);
                im.setLore(loreList);
            }
            is.setItemMeta(im);
        }
        inventory.setItem(slot, is);
    }

    public void click(int slot) {
        if(playerAvailableTrails.size() < 1) return;
        if(playerAvailableTrails.size() > offset + 36 && slot == 44) {
            openGUI(offset + 36);
            return;
        }
        if(offset >= 36 && slot == 36) {
            openGUI(offset - 36);
            return;
        }
        if(slot == 40) {
            plugin.getDataHandler().setPreference(player.getName(), null);
            player.sendMessage(dataHandler.getMessage("clearTrail"));
            player.closeInventory();
            return;
        }
        if(slot > 35) return;
        int i = 0;
        int offsetCount = 0;
        for(Trail t : playerAvailableTrails) {
            if(offsetCount < offset) {
                offsetCount++;
                continue;
            }
            if(i == slot) {
                plugin.getDataHandler().setPreference(player.getName(), t.getID());
                player.sendMessage(MessageFormat.format(dataHandler.getMessage("setTrail"), t.getID()));
                player.closeInventory();
                break;
            }
            i++;
        }
    }

    public List<Trail> getPlayerAvailableTrails() {
        List<Trail> playerAvailableTrails = new ArrayList<>();
        for(Trail t : plugin.getTrailsManager().getTrails()) {
            if(!player.hasPermission(t.getPermission())) continue;
            playerAvailableTrails.add(t);
        }
        return playerAvailableTrails;
    }

}
