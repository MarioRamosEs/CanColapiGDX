package alex.mario.game.LOGIC;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

public class Map_L {
    protected TiledMap tiledMap;
    public Map_L(String mapName){
        this.tiledMap = new TmxMapLoader().load(mapName);

    }

    public TiledMap getTiledMap() {
        return this.tiledMap;
    }

}
