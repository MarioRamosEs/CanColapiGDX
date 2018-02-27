package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;

import java.io.File;

public class Door extends Item {
    Texture doorClosed = TexturesSystem.getTexture("DoorClosed.png");
    Texture doorOpened = TexturesSystem.getTexture("DoorOpened.png");

    public Door(RectangleMapObject rectangleMapObject){
        super(rectangleMapObject);
        this.name = "Puerta";

        if(this.isPassable) this.texture = doorOpened;
        else this.texture = doorClosed;

        this.pos = pos;
        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());
    }

    @Override
    public void useGround(MyGdxGame game, Character character){
        Item key = character.hasItemType(game.getAvailableItems().get("key"));
        if(key != null){
            System.out.println("Has Item!!");
            open();
        }

    }
    @Override
    public void touch(MyGdxGame game, Character_L character){
        super.touch(game, character);
        Item key = character.hasItemType(game.getAvailableItems().get("key"));
        if(key != null){
            System.out.println("Has Item!!");
            open();
        }
    }

    private void open(){
        this.isPassable = true;
        this.texture = doorOpened;
    }

    private void close(){
        this.isPassable = false;
        this.texture = doorOpened;
    }
}
