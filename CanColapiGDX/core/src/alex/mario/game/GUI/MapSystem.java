package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.LOGIC.MapSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class MapSystem extends MapSystem_L{

    public MapSystem(MyGdxGame game){
        super(game);

    }

    public void loadMap(String filePath){
        super.loadMap(filePath);
    }

    public boolean isCharacterCollidingWithCollisionsLayer(Character_L character, Rectangle playerRectNewPosition){
        TiledMap characterTiledMap = character.getMap().getTiledMap();
        MapLayer collisionObjectLayer = characterTiledMap.getLayers().get("Collisions");
        MapObjects objects = collisionObjectLayer.getObjects();

        //Collide with objects
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerRectNewPosition)){

                return true;
            }
        }
        return false;
    }
    public boolean isCharacterCollidingWithAnyCharacter(Character_L character, Rectangle playerRectNewPosition){
        //Collide with characters
        ArrayList<Character> characters = (ArrayList<Character>)this.game.getCharacters().clone();
        characters.add(this.game.getPlayer());
        for (Character character2 : characters) {
            if(character2 == character){continue;}
            Rectangle rectangle = character2.getRect();
            if (Intersector.overlaps(rectangle, playerRectNewPosition)){
                return true;
            }
        }
        return false;
    }

}
