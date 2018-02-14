package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Player_L;
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



    public Player(CameraSystem cameraSystem, MapSystem mapSystem){
        super(mapSystem);

        this.position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        this.cameraSystem = cameraSystem;
        batch = new SpriteBatch();
        player = new Texture("pokeTest.png");

    }

    void draw(){
        //Pintado
        batch.begin();
        batch.setProjectionMatrix(cameraSystem.camera.combined);
        batch.draw(player, position.x, position.y);
        batch.end();
    }


}
