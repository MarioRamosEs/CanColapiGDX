package alex.mario.game.LOGIC;

import alex.mario.game.GUI.InventorySystem;
import alex.mario.game.GUI.Item;
import alex.mario.game.MyGdxGame;

import java.util.ArrayList;

public class InventorySystem_L {
    protected ArrayList<Item> items;
    protected MyGdxGame game;
    public InventorySystem_L(MyGdxGame game){
        this.game = game;
        this.items = new ArrayList<Item>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void add(Item item){
        this.items.add(item);
    }
}
