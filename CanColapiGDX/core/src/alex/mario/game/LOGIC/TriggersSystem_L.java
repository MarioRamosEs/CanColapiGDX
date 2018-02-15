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

    MyGdxGame game;
    TiledMap tiledMap;
    public TriggersSystem_L(MyGdxGame game) {
        this.game = game;
        this.tiledMap = this.game.getMapSystem().getTiledMap();
    }
    public void checkTriggers(Player_L player, Rectangle playerRectangle){
        MapLayer TriggerObjectLayer = tiledMap.getLayers().get("Triggers");
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
        }
    }
}
