package alex.mario.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch; //Buffer de pintado en tarjeta grafica
	private Texture img;
	private BitmapFont font;
	private int width, height;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
        renderizarPantalla();
        procesarEntrada();
	}

    private void procesarEntrada() {
    }

    private void renderizarPantalla() {
        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //batch.draw(img, 0, 0);
        font.draw(batch, "hola", 100, 100);
        batch.end();
    }

    @Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
