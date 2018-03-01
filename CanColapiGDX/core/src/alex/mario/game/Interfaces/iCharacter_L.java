package alex.mario.game.Interfaces;

import alex.mario.game.GUI.InventorySystem;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public interface iCharacter_L extends iSystem_L {
    boolean trigger(MapProperties mapProperties);
    boolean unTrigger(MapProperties mapProperties);
    Rectangle getRect(Vector2 playerPos);
    Rectangle getRect();

    void setDir(Vector2 newDir);
    void setPos(Vector2 newPos);

    Vector2 getDir();
    Vector2 getPos();

    int getLastDir();
    int getStep();

    Map getMap();
    void setMap(Map map);
    void addItem(Item item);
    ArrayList<Item> getItems();
    InventorySystem getInventorySystem();

    boolean isPassable();
    Item hasItemType(Class type);
    void setCollideState(Boolean newCollide);
    Boolean getCollideState();
    ArrayList<Item> hasItemsType(Class type);
    Vector2 getCenterPos();
}
