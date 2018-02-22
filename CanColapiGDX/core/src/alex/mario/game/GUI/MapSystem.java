package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.LOGIC.MapSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MapSystem extends MapSystem_L{

    public MapSystem(MyGdxGame game){
        super(game);

    }

    public void loadMap(String filePath){
        super.loadMap(filePath);
    }

    public boolean isCharacterColliding(Character_L character, Rectangle playerRectNewPosition){
        TiledMap characterTiledMap = character.getMap().getTiledMap();
        MapLayer collisionObjectLayer = characterTiledMap.getLayers().get("Collisions");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerRectNewPosition)){

                return true;
            }
        }
        return false;
    }


}
