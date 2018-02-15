package alex.mario.game.LOGIC;

import alex.mario.game.GUI.MapSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player_L {
    protected MapSystem mapSystem;
    protected MapLayer playerLayer;
    protected Vector2 position, direction, size;
    protected MyGdxGame game;
    protected int vel = 4;
    public Player_L(MyGdxGame game){
        this.game = game;
        this.mapSystem = this.game.getMapSystem();

        this.position = new Vector2(0,0);
        this.direction = new Vector2(0,0);
        this.size = new Vector2(20,50);
    }
    public void update(){
        
        //Movimiento
        Vector2 comprobacionTemporal = position.add(direction.cpy().scl(vel,vel));
        Rectangle rect = new Rectangle(comprobacionTemporal.x, comprobacionTemporal.y, this.size.x, this.size.y);

        if(!mapSystem.amIColliding(rect)){
            position.add(direction.cpy().scl(vel,vel)); //Movimiento básico
        }else{
            //direction = new Vector2(0,0);
        }
        //Compruebo Triggers
        mapSystem.checkTriggers(rect);

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
