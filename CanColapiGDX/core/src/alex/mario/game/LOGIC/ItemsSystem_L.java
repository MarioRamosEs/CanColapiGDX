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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemsSystem_L {
    protected MyGdxGame game;
    protected TiledMap tiledMap;
    protected ArrayList<Item> items;
    public ItemsSystem_L(MyGdxGame game, TiledMap tiledMap){
        this.game = game;
        this.tiledMap = tiledMap;

        this.items = loadItems(this.game, this.tiledMap);
    }
    public static ArrayList<Item> loadItems(MyGdxGame game, TiledMap tiledMap){
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

            String type = properties.get("type", "error", String.class);
            //items.add(new Key(isPicked, new Vector2(rectangle.getX(), rectangle.getY())));

            //REFLECTION:
            // http://tutorials.jenkov.com/java-reflection/constructors.html
            Iterator<String> keys = properties.getKeys();

            //Se llama a la función trigger para cada llave, incluimos todas las propiedades por si hace falta información
            //extra
            System.out.println("OBJECT: " + rectangleObject.getName());
            while(keys.hasNext()){
                String key = keys.next();
                System.out.println(key);
            }
            System.out.println("----------------");
            try {

                Class cl = game.getAvailableItems().get(type);
                Constructor constructor = cl.getConstructor(new Class[]{RectangleMapObject.class});
                items.add((Item)constructor.newInstance(rectangleObject));

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                System.out.println(properties.get("type", "UNDEFINED", String.class));
            }
        }
        return items;
    }
    public void update(){

    }
    public ArrayList<Item> getItems(){
        return this.items;
    }
}
