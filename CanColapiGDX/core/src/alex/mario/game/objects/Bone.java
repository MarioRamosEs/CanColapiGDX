package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Bone extends Item {

    public Bone(MyGdxGame game, Map map, RectangleMapObject rectangleMapObject){
        super(game, map, rectangleMapObject);
        this.name = "Bone";
        this.texture = TexturesSystem.getTexture("pokeBall.png");
        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());
    }

    @Override
    public void use(Character character){
        //todo dropear (o no)
    }
}
