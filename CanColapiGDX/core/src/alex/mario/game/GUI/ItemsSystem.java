package alex.mario.game.GUI;

import alex.mario.game.LOGIC.ItemsSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class ItemsSystem extends ItemsSystem_L {
    protected SpriteBatch spriteBatch;
    protected CameraSystem cameraSystem;
    public ItemsSystem(MyGdxGame game, TiledMap tiledMap){
        super(game, tiledMap);
        this.cameraSystem = this.game.getCameraSystem();
        this.spriteBatch = this.game.getSpriteBatch();
    }
    public void draw(){
        for(Item item : this.items){
            item.drawGround(this.spriteBatch, this.cameraSystem.getCamera());
        }
    }
}
