package alex.mario.game.GUI;

import alex.mario.game.LOGIC.CameraSystem_L;
import alex.mario.game.LOGIC.Map_L;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map extends Map_L {
    private TiledMapRenderer tiledMapRenderer;

    public Map(String mapName){
        super(mapName);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap);
    }
    public void DrawBackground(CameraSystem_L cameraSystem){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.tiledMapRenderer.setView(cameraSystem.getCamera());
        this.tiledMapRenderer.render(new int[]{0,1,2}); //Capa Floor Objects Wall
    }
    public void DrawForeground(){
        this.tiledMapRenderer.render(new int[]{5}); //Capa superior Objects
    }
}
