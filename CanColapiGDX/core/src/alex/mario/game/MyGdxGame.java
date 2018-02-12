package alex.mario.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	MapSystem ms;
	Player player;
	CameraSystem cs;

	@Override
	public void create () {
	    cs = new CameraSystem();
        ms = new MapSystem(cs);
        player = new Player(cs, ms);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
        ms.DrawBackground();
        player.draw();
        ms.DrawForeground();
	}

	@Override
	public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT)  player.setDir(new Vector2(-1,0));
        if(keycode == Input.Keys.RIGHT) player.setDir(new Vector2(1,0));
        if(keycode == Input.Keys.UP)    player.setDir(new Vector2(0,1));
        if(keycode == Input.Keys.DOWN)  player.setDir(new Vector2(0,-1));
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
        player.setDir(new Vector2(0,0));

		/*if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());*/
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}