package alex.mario.game.GUI;

import alex.mario.game.LOGIC.MapSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class MapSystem extends MapSystem_L{

    private TiledMapRenderer tiledMapRenderer;

    private CameraSystem cameraSystem;

    public MapSystem(MyGdxGame game){
        super(game);
        this.cameraSystem = game.getCameraSystem();
    }

    public void loadMap(String name){
        super.loadMap(name);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap);
    }

    public void DrawBackground(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(cameraSystem.getCamera());
        tiledMapRenderer.render(new int[]{0,1});
    }

    public void DrawForeground(){
        tiledMapRenderer.render(new int[]{2,4});
    }

    public boolean isPlayerRectColliding(Rectangle playerRectangle){
        MapLayer collisionObjectLayer = tiledMap.getLayers().get("Collisions");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerRectangle)){
                return true;
            }
        }

        return false;
    }


}
