package alex.mario.game.LOGIC;

import alex.mario.game.GUI.InventorySystem;
import alex.mario.game.GUI.Item;

import java.util.ArrayList;

public class InventorySystem_L {
    protected ArrayList<Item> items;
    public InventorySystem_L(){
        this.items = new ArrayList<Item>();
    }

    public void add(Item item){
        this.items.add(item);
    }
}
