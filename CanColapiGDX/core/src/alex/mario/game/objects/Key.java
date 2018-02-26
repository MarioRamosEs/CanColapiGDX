package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;

public class Key extends Item {
    public Key(RectangleMapObject rectangleMapObject){
        super(rectangleMapObject);
        this.name = "Llave";
        this.texture = new Texture("pokeBall.png");
        this.pos = pos;
        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());
    }

    public void useGround(MyGdxGame game, Character character){
        super.useGround(game, character);
        character.addItem(this);
    }
}
