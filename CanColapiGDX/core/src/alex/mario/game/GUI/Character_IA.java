package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Character_IA_L;
import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Character_IA extends Character_IA_L {
    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    //private Texture player;
    private TextureRegion[][] playerFrames;
    private String textureName = "Avatar-Red1.png";

    public Character_IA(MyGdxGame game, Map map){
        super(game, map);

        this.position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2 - 20);

        this.cameraSystem = game.getCameraSystem();

        batch = new SpriteBatch();
        playerFrames = TextureRegion.split(TexturesSystem.getTexture(textureName),48,64);
    }

    public void draw(){
        //getSentido();

        //Pintado
        batch.begin();
        batch.setProjectionMatrix(cameraSystem.getCamera().combined);
        batch.draw(playerFrames[getDirection()][getStep()], position.x, position.y);
        batch.end();
    }
}
