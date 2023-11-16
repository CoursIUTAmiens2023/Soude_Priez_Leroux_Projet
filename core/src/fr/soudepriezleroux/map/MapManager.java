package fr.soudepriezleroux.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.soudepriezleroux.entity.EntityManager;
import lombok.Getter;

public class MapManager {

    static Map map;

    public static void init(){
        map = new Map("map");
    }

    public static void render(){
        EntityManager.getBatch().begin();
        map.render();
        EntityManager.getBatch().end();
    }

}
