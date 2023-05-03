package pl.throwtrails.trails;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrailsManager {

    private final List<Trail> trails = new ArrayList<>();

    @Nullable
    public Trail getByName(String name) {
        for(Trail t : trails) {
            if(t.getID().equals(name)) return t;
        }
        return null;
    }

}
