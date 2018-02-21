package alex.mario.game.LOGIC;

import alex.mario.game.GUI.TriggersSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.io.File;

public class MapSystem_L {
    protected TiledMap tiledMap;
    protected MyGdxGame game;
    protected TriggersSystem triggersSystem;
    public MapSystem_L(MyGdxGame game){
        this.game = game;
        this.triggersSystem = this.game.getTriggersSystem();
    }
    public void loadMap(String name){
        //Load map
        name = "maps" + File.separator + name + ".tmx";
        System.out.println(name);

        tiledMap = new TmxMapLoader().load(name);
    }
    public TiledMap getTiledMap() {
        return this.tiledMap;
    }
}
