package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;

public class Door extends Item {
    public Door(RectangleMapObject rectangleMapObject){
        super(rectangleMapObject);
        this.name = "Puerta";
        this.texture = new Texture("pokeBall.png");
        this.pos = pos;
        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());
    }

    @Override
    public void useGround(MyGdxGame game, Character character){
        Item key = character.hasItemType(game.getAvailableItems().get("key"));
        if(key != null){
            System.out.println("Has Item!!");
            this.isPassable = true;
        }

    }
    @Override
    public void touch(MyGdxGame game, Character_L character){
        super.touch(game, character);
        Item key = character.hasItemType(game.getAvailableItems().get("key"));
        if(key != null){
            System.out.println("Has Item!!");
            this.isPassable = true;
        }
    }
}
