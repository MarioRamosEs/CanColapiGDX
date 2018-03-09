package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import alex.mario.game.Interfaces.iSystem_L;
import alex.mario.game.MyGdxGame;
import alex.mario.game.objects.Bone;
import alex.mario.game.objects.Key;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ItemsSystem_L implements iSystem_L {
    protected MyGdxGame game;
    protected Map map;
    protected TiledMap tiledMap;
    protected ArrayList<Item> items;

    public ItemsSystem_L(MyGdxGame game, TiledMap tiledMap, Map map) {
        this.game = game;
        this.tiledMap = tiledMap;
        this.map = map;

        this.items = loadItems(this.game, this.tiledMap, this.map);
    }

    public static ArrayList<Item> loadItems(MyGdxGame game, TiledMap tiledMap, Map map) {
        ArrayList<Item> items = new ArrayList<Item>();

        for (RectangleMapObject rectangleObject : tiledMap.getLayers().get("Items").getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();

            MapProperties properties = rectangleObject.getProperties();

            String type = properties.get("type", "error", String.class);

            //REFLECTION:
            // http://tutorials.jenkov.com/java-reflection/constructors.html
            //Se llama a la función trigger para cada llave, incluimos todas las propiedades por si hace falta información
            //extra

            try {
                Class cl = game.getAvailableItems().get(type);
                Constructor constructor = cl.getConstructor(new Class[]{MyGdxGame.class, Map.class, RectangleMapObject.class});
                items.add((Item) constructor.newInstance(game, map, rectangleObject));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println(properties.get("type", "UNDEFINED", String.class));
            }
        }
        return items;
    }

    public void update() {
        ListIterator<Item> iterator = this.items.listIterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            item.update();

            if (item.mustBeDeleted()) {
                iterator.remove();
            }
        }
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

}
