package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iSystem;
import alex.mario.game.LOGIC.ItemsSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class ItemsSystem extends ItemsSystem_L implements iSystem {
    protected SpriteBatch spriteBatch;
    protected CameraSystem cameraSystem;
    protected Matrix4 originalProjection;

    public ItemsSystem(MyGdxGame game, TiledMap tiledMap, Map map) {
        super(game, tiledMap, map);
        this.cameraSystem = this.game.getCameraSystem();
        this.spriteBatch = this.game.getSpriteBatch();

        this.originalProjection = this.spriteBatch.getProjectionMatrix().cpy();
    }

    public void draw() {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(this.cameraSystem.getCamera().combined);

        for (Item item : this.items) {
            item.drawGround(this.spriteBatch, this.cameraSystem.getCamera());
        }
        spriteBatch.setProjectionMatrix(this.originalProjection);
        spriteBatch.end();
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
