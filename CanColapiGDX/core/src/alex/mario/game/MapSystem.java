package alex.mario.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.io.File;
import java.util.Properties;
import static java.lang.System.getProperties;

public class MapSystem {
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    CameraSystem cs;
    TriggersSystem ts;

    MapSystem(CameraSystem cs, TriggersSystem ts){
        this.cs = cs;
        this.ts = ts;
    }

    void loadMap(String name){
        name = "maps" + File.separator + name + ".tmx";
        tiledMap = new TmxMapLoader().load(name);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    void DrawBackground(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cs.camera.update();
        tiledMapRenderer.setView(cs.camera);
        tiledMapRenderer.render(new int[]{0,1});
    }

    void DrawForeground(){
        tiledMapRenderer.render(new int[]{2,4});
    }

    public boolean colisiono(Rectangle playerRectangle){
        MapLayer collisionObjectLayer = tiledMap.getLayers().get("Colisiones");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerRectangle)){
                return true;
            }
        }

        return false;
    }

    public void comprobarTrigger(Rectangle playerRectangle){
        MapLayer TriggerObjectLayer = tiledMap.getLayers().get("Triggers");
        MapObjects triggers = TriggerObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : triggers.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            if (Intersector.overlaps(rectangle, playerRectangle)){
                ts.trigger(rectangleObject.getProperties());
            }
        }
    }
}
