package alex.mario.game.LOGIC;

import alex.mario.game.GUI.TriggersSystem;
import alex.mario.game.MyGdxGame;
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

    public Vector2 getEntryPos(String entryName) {
        //Obtenemos capa triggers
        MapLayer TriggerObjectLayer = this.tiledMap.getLayers().get("Triggers");
        MapObjects triggers = TriggerObjectLayer.getObjects();

        //Loop para toda la capa
        for (RectangleMapObject rectangleObject : triggers.getByType(RectangleMapObject.class)) {
            //Obtenemos las propiedades del objeto
            MapProperties mapProperties = rectangleObject.getProperties();
            //Preguntamos si contiene la llave "isLinkedTo" (entrada a la habitación) y preguntamos
            // si su valor es igual al que nos introducen por parámetro
            if(mapProperties.containsKey("isLinkedTo") && mapProperties.get("isLinkedTo").toString().equals(entryName)){
                //Devolvemos un vector con la posición del objeto (trigger) encontrado
                return new Vector2(rectangleObject.getRectangle().getX(), rectangleObject.getRectangle().getY());
            }
        }
        //Si no encuentra nada devolverá un null y petará xD
        return null;
    }
}
