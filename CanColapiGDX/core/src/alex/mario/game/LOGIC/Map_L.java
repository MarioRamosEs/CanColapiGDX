package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.ItemsSystem;
import alex.mario.game.MyGdxGame;
import alex.mario.game.characters.Dog;
import alex.mario.game.objects.Bone;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Map_L {
    protected TiledMap tiledMap;
    protected String mapName;
    protected ItemsSystem itemsSystem;
    protected MyGdxGame game;

    public Map_L(MyGdxGame game, String mapName){
        this.game = game;
        this.mapName = mapName;
        this.tiledMap = new TmxMapLoader().load(this.mapName);
    }

    public TiledMap getTiledMap() {
        return this.tiledMap;
    }
    public ArrayList<Item> getMapItems(){
            return this.itemsSystem.getItems();
    }
    public void removeItem(Item item){
        this.itemsSystem.removeItem(item);
    }
    public void addItem(Item item){
        this.itemsSystem.addItem(item);
    }

    public Item getClosestItemTo(Vector2 pos){
        ArrayList<Item> items = this.itemsSystem.getItems();
        if(items.size() <= 0){
            return null; //No items
        }

        Item closestItem = items.get(0);
        float closestItemDistance = closestItem.pos.dst(pos);
        for(Item item : this.itemsSystem.getItems()){
            if(closestItemDistance >= item.getCenterPos().dst(pos)){
                closestItem = item;
                closestItemDistance = item.getCenterPos().dst(pos);
            }
        }
        return closestItem;
    }

    public Bone getBone(){
        return this.itemsSystem.getBone();
    }

    public void update() {
        this.itemsSystem.update();
    }
}
