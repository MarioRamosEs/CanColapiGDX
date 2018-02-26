package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Item;
import alex.mario.game.MyGdxGame;
import alex.mario.game.objects.Key;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemsSystem_L {
    protected MyGdxGame game;
    protected TiledMap tiledMap;
    protected ArrayList<Item> items;
    public ItemsSystem_L(MyGdxGame game, TiledMap tiledMap){
        this.game = game;
        this.tiledMap = tiledMap;

        this.items = loadItems(this.tiledMap);
    }
    public static ArrayList<Item> loadItems(TiledMap tiledMap){
        ArrayList<Item> items = new ArrayList<Item>();
        /*for(MapObject object : tiledMap.getLayers().get("Items").getObjects()){
            MapProperties properties = object.getProperties();
            String isPicked = properties.get("isPicked").toString();
            items.add(new Key(isPicked.equals("true") ? true : false, new Vector2(properties.)));
        }
*/
        for (RectangleMapObject rectangleObject : tiledMap.getLayers().get("Items").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            MapProperties properties = rectangleObject.getProperties();

            Boolean isPicked = properties.get("isPicked", true, Boolean.class);
            items.add(new Key(isPicked, new Vector2(rectangle.getX(), rectangle.getY())));

        }
        return items;
    }
    public void update(){

    }
    public ArrayList<Item> getItems(){
        return this.items;
    }
}
