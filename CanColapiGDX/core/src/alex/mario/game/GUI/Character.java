package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iCharacter;
import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Character extends Character_L implements iCharacter {
    private SpriteBatch batch;
    private CameraSystem cameraSystem;
    protected TextureRegion[][] playerFrames;

    public Character(MyGdxGame game, Map map){
        super(game, map);

        this.inventorySystem = new InventorySystem(this.game, this);
        this.position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2 - 20);
        this.cameraSystem = game.getCameraSystem();

        batch = new SpriteBatch();
        playerFrames = TextureRegion.split(TexturesSystem.getTexture("fk002-brendan.png"), 32, 50);
    }

    public void draw(){
        //Pintado
        batch.begin();
        batch.setProjectionMatrix(cameraSystem.getCamera().combined);
        batch.draw(playerFrames[getLastDir()][getStep()], position.x, position.y);
        batch.end();
    }

    public void useSelectedItem(){
        if(this.selectedItem == null){
            this.game.getNotificationsSystem().addNotification("No tienes ningún objeto seleccionado!");
            return;
        }else{
            this.selectedItem.use(this);
        }
    }
}
