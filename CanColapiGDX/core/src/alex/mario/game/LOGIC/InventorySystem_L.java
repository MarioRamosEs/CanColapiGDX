package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.InventorySystem;
import alex.mario.game.GUI.Item;
import alex.mario.game.Interfaces.iSystem_L;
import alex.mario.game.MyGdxGame;

import java.util.ArrayList;

public class InventorySystem_L implements iSystem_L {
    protected ArrayList<Item> items;
    protected Character character;
    protected MyGdxGame game;
    public InventorySystem_L(MyGdxGame game, Character character){
        this.game = game;
        this.items = new ArrayList<Item>();
        this.character = character;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void add(Item item){
        this.items.add(item);
    }

    public void update() {

    }
}
