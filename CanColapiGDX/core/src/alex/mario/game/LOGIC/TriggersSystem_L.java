package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Player;
import alex.mario.game.GUI.TriggersSystem;
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
    public void checkTriggers(Player_L player, Rectangle playerRectangle){
        /// BUGG WITH this.tiledMap not updated ///
        // MapLayer TriggerObjectLayer = this.tiledMap.getLayers().get("Triggers");
        //////
        MapLayer TriggerObjectLayer = this.game.getMapSystem().getTiledMap().getLayers().get("Triggers");
        MapObjects triggers = TriggerObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : triggers.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            MapProperties mapProperties = rectangleObject.getProperties();
            Iterator<String> keys = mapProperties.getKeys();

            while(keys.hasNext()){
                String key = keys.next();
                this.triggers(
                        player,
                        key,
                        mapProperties.get(key).toString(),
                        Intersector.overlaps(rectangle, playerRectangle)
                );
            }

        }
    }
    public void triggers(Player_L player, String triggerName, String triggerValue, boolean triggered){
        switch(triggerName){
            case "EnviarMensaje":
                if(triggered){
                    if(player.trigger(triggerName)){
                        System.out.println("Activado xD");
                    }
                }else{
                    if(player.untrigger(triggerName)){
                        System.out.println("Desactivado xD");
                    }
                }
                break;
            case "CambioMapa":
                if(triggered){
                    this.game.loadMap(triggerValue);
                    player.resetPos();
                }
                break;
            case "CameraZoom":
                if(triggered){ //Zoom
                    if(player.trigger(triggerName)) {
                        this.game.getCameraSystem().proportionalZoom(Float.parseFloat(triggerValue));
                    }
                }else { //DesZoom
                    if (player.untrigger(triggerName)) {
                        this.game.getCameraSystem().proportionalZoom(-Float.parseFloat(triggerValue));
                    }
                }
                break;
        }
    }
}
