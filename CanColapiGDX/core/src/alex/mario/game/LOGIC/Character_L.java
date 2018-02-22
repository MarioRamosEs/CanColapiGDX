package alex.mario.game.LOGIC;

import alex.mario.game.GUI.InventorySystem;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.MapSystem;
import alex.mario.game.GUI.TriggersSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Character_L {
    protected MapSystem mapSystem;
    protected TriggersSystem triggersSystem;
    protected Map map;
    protected Vector2 position, direction, size;
    protected MyGdxGame game;
    protected int vel = 4;
    protected ArrayList<String> triggeredBy;

    protected int lastDir = 0;
    protected long millis = System.currentTimeMillis();
    protected int step;

    protected InventorySystem inventorySystem;

    public Character_L(MyGdxGame game, Map map){
        this.game = game;
        this.map = map;
        this.mapSystem = this.game.getMapSystem();
        this.triggersSystem = this.game.getTriggersSystem();

        this.triggeredBy = new ArrayList<String>();

        this.position = new Vector2(0,0);
        this.direction = new Vector2(0,0);
        this.size = new Vector2(40,32);
    }
    public void update(){

        //Movimiento
        Vector2 newPosition = position.cpy().add(direction.cpy().scl(vel,vel));
        Rectangle playerRectNewPosition = this.getRect(newPosition);

        if(!mapSystem.isCharacterCollidingWithCollisionsLayer(this, playerRectNewPosition) && !mapSystem.isCharacterCollidingWithAnyCharacter(this, playerRectNewPosition)) {

            position.add(direction.cpy().scl(vel, vel)); //Movimiento básico
        }

        //Compruebo Triggers
        this.triggersSystem.checkTriggers(this, playerRectNewPosition);

    }
    public boolean trigger(String triggerName){
        if(this.triggeredBy.contains(triggerName)){
            return false;
        }else{
            this.triggeredBy.add(triggerName);
            return true;
        }
    }
    public boolean untrigger(String triggerName){
        if(!this.triggeredBy.contains(triggerName)){
            return false;
        }else{
            this.triggeredBy.remove(triggerName);
            return true;
        }
    }
    public Rectangle getRect(Vector2 playerPos){
        return new Rectangle(playerPos.x, playerPos.y, this.size.x, this.size.y);
    }
    public Rectangle getRect(){
        return this.getRect(this.position);
    }
    public void setDir(Vector2 newDir){
        this.direction = newDir;
    }
    public void resetPos(){
        position = new Vector2(Gdx.graphics.getWidth()/2 ,Gdx.graphics.getHeight()/2);}
    public void setPos(Vector2 newPos){
        this.position = newPos;
    }
    public Vector2 getDir(){
        return this.direction;
    }
    public Vector2 getPosition(){
        return this.position;
    }

    protected int getDirection(){ //TODO hacer cambios temporales
        if(direction.x < 0){
            lastDir = 1;
            return 1;
        }
        if(direction.x > 0){
            lastDir = 2;
            return 2;
        }
        if(direction.y < 0){
            lastDir = 0;
            return 0;
        }
        if(direction.y > 0){
            lastDir = 3;
            return 3;
        }

        step = 0;
        return lastDir;
    }

    protected int getStep(){
        if(System.currentTimeMillis() > millis+200 && !direction.epsilonEquals(0,0)){
            millis = System.currentTimeMillis();
            if(step == 0) step++;
            step += 2;
            if(step > 3) step = 1;
        }

        return step;
    }
    public Map getMap(){
        return this.map;
    }
    public void setMap(Map map){
        this.map = map;
    }

    public InventorySystem getInventorySystem() {
        return inventorySystem;
    }
}
