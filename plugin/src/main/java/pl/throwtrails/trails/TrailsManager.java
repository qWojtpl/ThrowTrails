package pl.throwtrails.trails;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.HashMap;

@Getter
public class TrailsManager {

    private final HashMap<String, Trail> trails = new HashMap<>();

    @Nullable
    public Trail getByName(String name) {
        return trails.getOrDefault(name, null);
    }

}
