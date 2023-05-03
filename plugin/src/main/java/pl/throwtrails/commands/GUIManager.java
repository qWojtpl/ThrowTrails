package pl.throwtrails.commands;

import lombok.Getter;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GUIManager {

    private final List<GUIHandler> GUIs = new ArrayList<>();

    public boolean isFromHandler(Inventory inventory) {
        return (getHandler(inventory) != null);
    }

    @Nullable
    public GUIHandler getHandler(Inventory inventory) {
        for(GUIHandler i : GUIs) {
            if(i.getInventory().equals(inventory)) return i;
        }
        return null;
    }

    public void removeHandler(Inventory inventory) {
        int i = -1;
        for(GUIHandler gui : GUIs) {
            i++;
            if(gui.getInventory().equals(inventory)) break;
        }
        if(i >= 0) GUIs.remove(i);
    }

}
