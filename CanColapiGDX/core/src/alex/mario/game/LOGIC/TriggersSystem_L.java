package alex.mario.game.LOGIC;

import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class TriggersSystem_L {

    protected MyGdxGame game;
    protected TiledMap tiledMap;
    public TriggersSystem_L(MyGdxGame game) {
        this.game = game;
        this.tiledMap = this.game.getMapSystem().getTiledMap();//NOT WORKING PROPERLY

    }
    public void checkTriggers(Character_L character, Rectangle playerRectangle){
        /// BUGG WITH this.tiledMap not updated ///
        // MapLayer TriggerObjectLayer = this.tiledMap.getLayers().get("Triggers");
        //////
        MapLayer TriggerObjectLayer = this.game.getMapSystem().getTiledMap().getLayers().get("Triggers");
        MapObjects triggers = TriggerObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : triggers.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            MapProperties mapProperties = rectangleObject.getProperties();
            Iterator<String> keys = mapProperties.getKeys();

            //Se llama a la función trigger para cada llave, incluimos todas las propiedades por si hace falta información
            //extra
            while(keys.hasNext()){
                String key = keys.next();
                this.triggers(
                        character,
                        key,
                        mapProperties.get(key).toString(),
                        Intersector.overlaps(rectangle, playerRectangle),
                        mapProperties//Todas las propiedades del objeto
                );
            }

        }
    }
    public void triggers(Character_L character, String triggerName, String triggerValue, boolean triggered, MapProperties mapProperties){
        switch(triggerName){
            case "EnviarMensaje":
                if(triggered){
                    if(character.trigger(triggerName)){
                        this.game.getNotificationsSystem().addNotification("Has pisado la alfombra...");
                    }
                }else{
                    if(character.untrigger(triggerName)){
                        this.game.getNotificationsSystem().addNotification("Gracias por dejar de pisar la alfombra!");
                    }
                }
                break;
            case "CambioMapa":
                if(triggered){
                    this.game.loadMap(triggerValue);
                    //character.resetPos();

                    //Get value of key "linkTo":
                    String linkTo = mapProperties.get("linkTo").toString();
                    character.setPos(this.game.getMapSystem().getEntryPos(linkTo));
                }
                break;
            case "CameraZoom":
                if(triggered){ //Zoom
                    if(character.trigger(triggerName)) {
                        this.game.getCameraSystem().proportionalZoom(Float.parseFloat(triggerValue));
                    }
                }else { //DesZoom
                    if (character.untrigger(triggerName)) {
                        this.game.getCameraSystem().proportionalZoom(-Float.parseFloat(triggerValue));
                    }
                }
                break;
        }
    }
}
