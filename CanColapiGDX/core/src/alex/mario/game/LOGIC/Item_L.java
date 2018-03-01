package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Character;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Item_L {
    protected String name;
    protected String description;
    protected Vector2 pos;
    protected Vector2 size;
    protected boolean isPassable;

    protected HashMap<Character_L, Double> touchedBy;

    public Item_L(RectangleMapObject rectangleMapObject){
        this.name = rectangleMapObject.getProperties().get("name", "UNDEFINED", String.class);
        this.pos = new Vector2(rectangleMapObject.getRectangle().getX(), rectangleMapObject.getRectangle().getY());
        this.size = new Vector2(rectangleMapObject.getRectangle().getWidth(), rectangleMapObject.getRectangle().getHeight());
        this.isPassable = rectangleMapObject.getProperties().get("isPassable", false, Boolean.class);
        this.touchedBy = new HashMap<Character_L, Double>();
    }
    public Rectangle getRectangle() {
        return new Rectangle(this.pos.x, this.pos.y, this.size.x, this.size.y);
    }

    public boolean isPassable() {
        return this.isPassable;
    }
    public void touch(MyGdxGame game, Character_L character){
        if(!this.touchedBy.containsKey(character)){
            this.touchedBy.put(character, Double.parseDouble(character.getPos().dst(this.getPos())+""));
            //Object touchec
        }
    }
    public void untouch(MyGdxGame game, Character_L character){
        this.touchedBy.remove(character);
    }
    public void use(MyGdxGame game, Character character){
        return;
    }
    public void useGround(MyGdxGame game, Character character){
        return;
    }
    public Vector2 getPos(){
        return this.pos;
    }
    public Vector2 getCenterPos(){
        return this.pos.cpy().add(this.size.cpy().scl(0.5f, 0.5f));
    }
}
