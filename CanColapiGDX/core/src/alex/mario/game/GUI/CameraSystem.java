package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iSystem;
import alex.mario.game.LOGIC.CameraSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CameraSystem extends CameraSystem_L implements iSystem {
    //CLASS vacía, pero aquí debería ir lo relacionado con HUD, mostrar cosas en pantalla etc (la cámara es lo último que se dibuja)
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private SpriteBatch batch;

    public CameraSystem(MyGdxGame game){
        super(game);
        this.spriteBatch = this.game.getSpriteBatch();
        this.shapeRenderer = this.game.getShapeRenderer();
        this.font = this.game.getNotificationsFont();
        this.batch = new SpriteBatch();
    }

    public void draw(){
        //Se actualiza la cámara Orthographic
        this.camera.update();

        //GAMEOVER
        if(this.game.isGameOver()){
            batch.begin();
            //batch.setProjectionMatrix(game.getCameraSystem().getCamera().combined);
            batch.draw(TexturesSystem.getTexture("youDied.png"), 0, 0);
            batch.end();
        }

        //FPS COUNTER
        shapeRenderer.begin();
            //Background
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Gdx.graphics.getFramesPerSecond() > 50 ? Color.GREEN : (Gdx.graphics.getFramesPerSecond() > 30 ? Color.YELLOW : Color.RED));
        shapeRenderer.rect(
                5, this.h - 25,
                60, 20
        );
        shapeRenderer.end();
        this.spriteBatch.begin();
        this.font.draw(
                this.spriteBatch,
                Gdx.graphics.getFramesPerSecond() + " FPS",
                10, this.h - 10);
        this.spriteBatch.end();
    }
}
