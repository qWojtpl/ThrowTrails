package pl.throwtrails.trails;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class TrailsManager {

    private final HashMap<String, Trail> trails = new HashMap<>();

}
