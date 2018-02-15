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


    private Texture player;

    private TextureRegion textureRegion;



    public Player(MyGdxGame game){
        super(game);

        this.position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        this.cameraSystem = game.getCameraSystem();

        batch = new SpriteBatch();
        player = new Texture("pokeTest.png");

    }

    public void draw(){
        //Pintado
        batch.begin();
        batch.setProjectionMatrix(cameraSystem.getCamera().combined);
        batch.draw(player, position.x, position.y);
        batch.end();
    }


}
