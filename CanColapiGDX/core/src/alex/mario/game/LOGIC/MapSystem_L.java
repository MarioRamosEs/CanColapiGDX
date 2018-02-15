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
        tiledMap = new TmxMapLoader().load(name);
    }
    public void checkTriggers(Rectangle playerRectangle){
        MapLayer TriggerObjectLayer = tiledMap.getLayers().get("Triggers");
        MapObjects triggers = TriggerObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : triggers.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            if (Intersector.overlaps(rectangle, playerRectangle)){
                this.triggersSystem.trigger(rectangleObject.getProperties());
            }
        }
    }
}
