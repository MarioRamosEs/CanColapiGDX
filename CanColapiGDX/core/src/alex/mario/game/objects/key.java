package alex.mario.game.objects;

import alex.mario.game.GUI.Item;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Key extends Item {
    public Key(Boolean isPicked, Vector2 pos){
        super(isPicked);
        this.texture = new Texture("pokeBall.png");
        this.pos = pos;
    }
}
