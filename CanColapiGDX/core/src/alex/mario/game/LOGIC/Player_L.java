package alex.mario.game.LOGIC;

import alex.mario.game.GUI.MapSystem;
import alex.mario.game.GUI.TriggersSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player_L {
    protected MapSystem mapSystem;
    protected TriggersSystem triggersSystem;
    protected MapLayer playerLayer;
    protected Vector2 position, direction, size;
    protected MyGdxGame game;
    protected int vel = 4;
    protected ArrayList<String> triggeredBy;

    public Player_L(MyGdxGame game){
        this.game = game;
        this.mapSystem = this.game.getMapSystem();
        this.triggersSystem = this.game.getTriggersSystem();

        this.triggeredBy = new ArrayList<String>();

        this.position = new Vector2(0,0);
        this.direction = new Vector2(0,0);
        this.size = new Vector2(20,30);
    }
    public void update(){
        
        //Movimiento
        Vector2 newPosition = position.cpy().add(direction.cpy().scl(vel,vel));
        Rectangle playerRectNewPosition = this.getRect(newPosition);

        if(!mapSystem.isPlayerRectColliding(playerRectNewPosition)){
            position.add(direction.cpy().scl(vel,vel)); //Movimiento básico
        }else{
            //direction = new Vector2(0,0);
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
        position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);}
    public void setPos(Vector2 newPos){
        this.position = newPos;
    }
    public Vector2 getDir(){
        return this.direction;
    }
    public Vector2 getPosition(){
        return this.position;
    }

}
