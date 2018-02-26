package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.TriggersSystem;
import alex.mario.game.MyGdxGame;
import alex.mario.game.LOGIC.Character_L;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class MapSystem_L {

    protected MyGdxGame game;
    protected HashMap<String, Map> maps;

    public MapSystem_L(MyGdxGame game){
        this.game = game;
        this.maps = new HashMap<String, Map>();
    }
    public void loadMap(String filePath){
        //Load map

        System.out.println(filePath);
        if(!this.maps.containsKey(filePath)){
            this.maps.put(filePath, new Map(this.game, filePath));
        }
        System.out.println("CARGADO!");
    }

    public Vector2 getEntryPos(Map map, String entryName) {
        RectangleMapObject exitObject = this.genericMapObjectSearch(map, "isLinkedTo", entryName);
        if(exitObject == null){
            return null;
        }else{
            return new Vector2(
                    exitObject.getRectangle().getX(),
                    exitObject.getRectangle().getY()
            );
        }
    }
    public Vector2 getExitPos(Map map, String entryName) {
        RectangleMapObject exitObject = this.genericMapObjectSearch(map, "linkTo", entryName);
        if(exitObject == null){
            return null;
        }else{
            return new Vector2(
                    exitObject.getRectangle().getX(),
                    exitObject.getRectangle().getY()
            );
        }
    }
    public RectangleMapObject genericMapObjectSearch(Map map, String key, String value){
        //Obtenemos capa triggers
        MapLayer TriggerObjectLayer = map.getTiledMap().getLayers().get("Triggers");
        MapObjects triggers = TriggerObjectLayer.getObjects();

        //Loop para toda la capa
        for (RectangleMapObject rectangleObject : triggers.getByType(RectangleMapObject.class)) {
            //Obtenemos las propiedades del objeto
            MapProperties mapProperties = rectangleObject.getProperties();
            //Preguntamos si contiene la llave key y preguntamos
            // si su valor es igual al que nos introducen por parámetro value
            if(mapProperties.containsKey(key) && mapProperties.get(key).toString().equals(value)){
                //Devolvemos el objeto entero
                return rectangleObject;
            }
        }
        //Si no encuentra nada devolverá un null y petará xD
        return null;
    }
    public Map getMap(String mapName){
        if(this.maps.containsKey(mapName)){
            return this.maps.get(mapName);
        }else{
            return null;
        }
    }
}
