package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.ItemsSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

public class Map_L {
    protected TiledMap tiledMap;
    protected ItemsSystem itemsSystem;
    protected MyGdxGame game;
    public Map_L(MyGdxGame game, String mapName){
        this.game = game;
        this.tiledMap = new TmxMapLoader().load(mapName);
    }

    public TiledMap getTiledMap() {
        return this.tiledMap;
    }
    public ArrayList<Item> getMapItems(){
            return this.itemsSystem.getItems();
    }
}
