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
import java.util.ArrayList;

public class Door extends Item {
    protected Texture doorClosed = TexturesSystem.getTexture("DoorClosed.png");
    protected Texture doorOpened = TexturesSystem.getTexture("DoorOpened.png");

    protected String doorCode;

    protected boolean locked = true;

    public Door(RectangleMapObject rectangleMapObject){
        super(rectangleMapObject);
        this.name = "Puerta";

        if(this.isPassable) this.texture = doorOpened;
        else this.texture = doorClosed;

        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());

        this.doorCode = rectangleMapObject.getProperties().get("doorCode").toString();
    }

    @Override
    public void useGround(MyGdxGame game, Character character){
        if(this.locked) {
            ArrayList<Item> itemsKey = character.hasItemsType(game.getAvailableItems().get("key"));
            System.out.println(itemsKey.size());
            //Obtenemos todos los objetos del tipo key
            for (Item itemKey : itemsKey) {
                Key key = (Key) itemKey;//Casteamos a key
                if (key.getKeyCode().equals(this.doorCode)) {
                    //Comprobamos si el código coincide
                    game.getNotificationsSystem().addNotification("Has abierto la puerta con código: '" + this.doorCode + "'.");
                    this.locked = false;
                    this.open();
                    return;
                } else {
                    //La llave no tiene el código de la puerta

                }
            }
            //No tiene ninguna llave con el código de la puerta
            game.getNotificationsSystem().addNotification("No puedo abrir esta puerta.");
            return;
        }else{
            if(this.isPassable){
                this.close();
            }else{
                this.open();
            }
        }

    }
    @Override
    public void touch(MyGdxGame game, Character_L character){
        //super.touch(game, character);
        /*Item itemKey = character.hasItemType(game.getAvailableItems().get("key"));
        if(itemKey != null){
            Key key = (Key)itemKey;
            if(key.getKeyCode().equals(this.doorCode)){
                System.out.println("Has Item!!");
                open();
            }

        }*/
    }

    private void open(){
        this.isPassable = true;
        this.texture = doorOpened;
    }

    private void close(){
        this.isPassable = false;
        this.texture = doorClosed;
    }

    public String getDoorCode(){
        return this.doorCode;
    }
}
