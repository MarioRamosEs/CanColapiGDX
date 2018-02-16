package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Player_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends Player_L{
    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    //private Texture player;
    private TextureRegion[][] playerFrames;

    public Player(MyGdxGame game){
        super(game);

        this.position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        this.cameraSystem = game.getCameraSystem();

        batch = new SpriteBatch();
        playerFrames = TextureRegion.split(new Texture("Avatar-Red1.png"),48,64);
    }

    public void draw(){
        //getSentido();

        //Pintado
        batch.begin();
        batch.setProjectionMatrix(cameraSystem.getCamera().combined);
        int tempStep = getStep();
        System.out.println(tempStep);
        batch.draw(playerFrames[getSentido()][tempStep], position.x, position.y);
        batch.end();
    }
}