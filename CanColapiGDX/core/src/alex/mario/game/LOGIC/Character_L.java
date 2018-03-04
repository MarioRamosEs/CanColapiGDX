package alex.mario.game.LOGIC;

import alex.mario.game.GUI.*;
import alex.mario.game.Interfaces.iCharacter_L;
import alex.mario.game.Interfaces.iSystem_L;
import alex.mario.game.MyGdxGame;
import alex.mario.game.objects.Key;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class Character_L implements iCharacter_L {
    public static final int DEFAULT_VEL = 4;
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

    protected Boolean collides;

    protected InventorySystem inventorySystem;

    protected boolean isPassable = false;
    protected boolean isRunning = false;
    protected boolean isHiding = false;

    public Character_L(MyGdxGame game, Map map){
        this.game = game;
        this.map = map;
        this.mapSystem = this.game.getMapSystem();
        this.triggersSystem = this.game.getTriggersSystem();
        this.inventorySystem = new InventorySystem(this.game);

        this.collides = true;

        this.triggeredBy = new ArrayList<String>();

        this.position = new Vector2(0,0);
        this.direction = new Vector2(0,0);
        this.size = new Vector2(32,40); //Valor ajustado a mano, no sobreescribir.
    }
    public void update(){
        if(this.isRunning){this.vel = DEFAULT_VEL * 2;}else{this.vel = DEFAULT_VEL;}
        if(!this.collides){
            position.add(direction.cpy().scl(vel, vel)); //Movimiento básico
            return;
        }
        //Movimiento
        Vector2 newPosition = position.cpy().add(direction.cpy().scl(vel,vel));
        Rectangle playerRectNewPosition = this.getRect(newPosition);

        if(!mapSystem.isCharacterCollidingWithCollisionsLayer(this, playerRectNewPosition)
           && !mapSystem.isCharacterCollidingWithItem(this, playerRectNewPosition)
                ) {
            if(!mapSystem.isCharacterCollidingWithAnyCharacter(this, playerRectNewPosition)){

                position.add(direction.cpy().scl(vel, vel)); //Movimiento básico
            }else{
                position.add(direction.cpy().scl(0.5f, 0.5f)); //Movimiento reducido atravesando jugadores
            }
        }

        //Compruebo Triggers
        this.triggersSystem.checkTriggers(this, playerRectNewPosition);

    }

    public boolean trigger(String triggerId){
        if(this.triggeredBy.contains(triggerId)){
            return false;
        }else{
            this.triggeredBy.add(triggerId);
            return true;
        }
    }
    public boolean unTrigger(String triggerId, boolean justOnce){
        if(justOnce){return false;}
        if(!this.triggeredBy.contains(triggerId)){
            return false;
        }else{
            this.triggeredBy.remove(triggerId);
            return true;
        }
    }
    public boolean trigger(MapProperties mapProperties){
        return this.trigger(mapProperties.get("id").toString());
    }
    public boolean unTrigger(MapProperties mapProperties){
        return this.unTrigger(mapProperties.get("id").toString(), mapProperties.get("justOnce", false, Boolean.class));
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
        this.position = newPos.cpy().sub(this.size.x / 2, this.size.y / 2);
    }
    public Vector2 getDir(){
        return this.direction;
    }
    public Vector2 getPos(){
        return this.position;
    }

    public int getLastDir(){ //TODO hacer cambios temporales
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
    public Vector2 getLastDirVector2(){ //TODO hacer cambios temporales
        switch (this.getLastDir()){
            case 0:
                return MyGdxGame.DIRECTION_DOWN;
            case 1:
                return MyGdxGame.DIRECTION_LEFT;
            case 2:
                return MyGdxGame.DIRECTION_RIGHT;
            case 3:
                return MyGdxGame.DIRECTION_UP;

        }

        return new Vector2();
    }
    public int getStep(){
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

    public void addItem(Item item){
        this.game.getNotificationsSystem().addNotification("Se ha añadido '" + item.getName() + "' al inventario");
        this.inventorySystem.add(item);
    }
    public ArrayList<Item> getItems(){
        return this.inventorySystem.getItems();
    }
    public InventorySystem getInventorySystem() {
        return inventorySystem;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public Item hasItemType(Class type) {
        for(Item item : this.inventorySystem.items){
            if(item.getClass() == type){
                return item;
            }
        }
        return null;
    }
    public ArrayList<Item> hasItemsType(Class type) {
        ArrayList<Item> itemsFound = new ArrayList<Item>();
        for(Item item : this.inventorySystem.items){
            if(item.getClass() == type){
                itemsFound.add(item);
            }
        }
        return itemsFound;
    }
    public void setCollideState(Boolean newCollide){
        this.collides = newCollide;
    }
    public Boolean getCollideState(){
        return this.collides;
    }
    public Vector2 getCenterPos(){
        return this.position.cpy().add(this.size.cpy().scl(0.5f, 0.5f));
    }
    public boolean isHiding() {
        return this.isHiding;
    }
    public void setIsHiding(boolean newValue){
        this.isHiding = newValue;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setIsRunning(boolean running) {
        this.isRunning = running;
    }
}
