package alex.mario.game.objects;

import alex.mario.game.GUI.*;
import alex.mario.game.GUI.Character;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.xml.soap.Text;
import java.util.ArrayList;

public class Bullet extends Item {
    public static final int BULLET_MAX_DISTANCE = 10000;
    public static final int BULLET_SPEED = 6;

    private MapLayer collisionsLayer;
    private MapLayer itemsLayer;
    private ArrayList<Character_IA> characters;
    private Vector2 initialPos;
    private Vector2 direction;
    private Character owner;

    public Bullet(MyGdxGame game, Map map, RectangleMapObject rectangleMapObject) {
        super(game, map, rectangleMapObject);
        this.isPassable = true;
        this.isVisible = true;

        this.collisionsLayer = this.map.getTiledMap().getLayers().get("Collisions");
        this.itemsLayer = this.map.getTiledMap().getLayers().get("Items");
        this.characters = this.game.getCharacters();

        this.direction = new Vector2();
        this.initialPos = this.pos;
        this.texture = TexturesSystem.getTexture("bullet.png");
    }
    @Override
    public void update(){
        if(this.initialPos.dst(this.pos) > BULLET_MAX_DISTANCE){
            this.mustBeDeleted = true;

            return;
        }

        this.pos.add(this.direction.cpy().scl(BULLET_SPEED, BULLET_SPEED)); //Movimiento básico
        Rectangle rect = new Rectangle(this.pos.x,this.pos.y, this.size.x, this.size.y);
        //Check collisions
        for(Character_IA character : this.characters){
            if(rect.overlaps(character.getRect())){
                character.getDamage(20);
                this.mustBeDeleted = true;
                return;
            }
        }

        for(RectangleMapObject rectangleMapObject : this.collisionsLayer.getObjects().getByType(RectangleMapObject.class)){
            if(rect.overlaps(rectangleMapObject.getRectangle())){
                this.mustBeDeleted = true;
                return;
            }
        }
    }
    public Vector2 getDirection() {
        return this.direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
        if(direction == MyGdxGame.DIRECTION_DOWN){
        }
    }

    public Character getOwner() {
        return owner;
    }

    public void setOwner(Character character) {
        this.owner = owner;
    }
}
