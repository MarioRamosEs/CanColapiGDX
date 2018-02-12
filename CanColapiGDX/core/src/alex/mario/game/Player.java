package alex.mario.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;

public class Player {
    SpriteBatch batch;
    CameraSystem cs;

    float x, y;
    Texture player;
    MapLayer playerLayer;
    TextureRegion textureRegion;

    Player(CameraSystem cs){
        this.cs = cs;
        batch = new SpriteBatch();
        player = new Texture("pokeTest.png");
    }

    void draw(){
        batch.begin();
        batch.setProjectionMatrix(cs.camera.combined);
        batch.draw(player, cs.camera.position.x, cs.camera.position.y);
        batch.end();
    }
}
