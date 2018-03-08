package alex.mario.game;

import alex.mario.game.GUI.*;
import alex.mario.game.GUI.Character;
import alex.mario.game.Interfaces.iSystem;
import alex.mario.game.Interfaces.iSystem_L;
import alex.mario.game.characters.Dog;
import alex.mario.game.characters.Ghost;
import alex.mario.game.characters.Player;
import alex.mario.game.objects.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	private MapSystem mapSystem;
	private Player player;
	private CameraSystem cameraSystem;
	private TriggersSystem triggersSystem;
	private NotificationsSystem notificationsSystem;
	private TexturesSystem texturesSystem;
	private SoundsSystem soundsSystem;

	private BitmapFont notificationsFont;
	private BitmapFont inventoryFont;
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;

	private ArrayList<Character_IA> characters;


	public static final float DISTANCE_USEGROUND_ITEM = 60f;

	protected HashMap<String, Class> availableItems;
	protected HashMap<String, Class> availableCharacters_IA;

	public static final Vector2 DEFAULT_TILE_SIZE= new Vector2(32, 32);

	//Directions
	public static final Vector2 DIRECTION_UP = new Vector2(0, 1);
	public static final Vector2 DIRECTION_LEFT = new Vector2(-1, 0);
	public static final Vector2 DIRECTION_RIGHT = new Vector2(1, 0);
	public static final Vector2 DIRECTION_DOWN = new Vector2(0, -1);
	private Vector2[] directions = new Vector2[]{DIRECTION_LEFT, DIRECTION_DOWN, DIRECTION_RIGHT, DIRECTION_UP};

	@Override
	public void create () {
		this.spriteBatch = new SpriteBatch();
		this.texturesSystem = new TexturesSystem();
		this.soundsSystem = new SoundsSystem();

		this.notificationsFont = new BitmapFont();

		this.inventoryFont = new BitmapFont();
		this.inventoryFont.getData().setScale(0.9f);

		this.availableItems = this.loadAvailableItems();
		this.availableCharacters_IA = this.loadAvailableCharacters_IA();

		this.shapeRenderer = new ShapeRenderer();

		//Creamos toda la pesca
	    this.cameraSystem = new CameraSystem(this);
        this.mapSystem = new MapSystem(this);
		this.triggersSystem = new TriggersSystem(this);

		//Cargamos el mapa
		this.mapSystem.loadMap(formatToFilePath("Planta1"));
		//this.loadMap("Planta1");

		player = new Player(this, this.mapSystem.getMap(formatToFilePath("Planta1")));
		player.setPos(this.mapSystem.getEntryPos(player.getMap(), "start"));

		this.characters = new ArrayList<Character_IA>();

		this.notificationsSystem = new NotificationsSystem(this);
		Gdx.input.setInputProcessor(this);

		System.out.println("ALL LOADED OK");
	}

	private HashMap<String,Class> loadAvailableCharacters_IA() {
		HashMap<String, Class> ret = new HashMap<String, Class>();

		ret.put("ghost", Ghost.class);
		ret.put("dog", Dog.class);
		return ret;
	}

	public static HashMap<String,Class> loadAvailableItems() {
		HashMap<String, Class> ret = new HashMap<String, Class>();

		ret.put("key", Key.class);
		ret.put("door", Door.class);
		ret.put("ouija", Ouija.class);
		ret.put("characterSpawner", CharacterSpawner.class);
		ret.put("bone", Bone.class);

		return ret;
	}

	@Override
	public void render () {
		//Bucle principal

		//Update todas las variables, movimiento, etc
		this.update();

		//Dibujamos
		this.draw();
	}
	private void update(){
		//Actualizamos al jugador
		player.update();

		this.mapSystem.update();

		this.notificationsSystem.update();

		//Actualizamos la cámara
		this.cameraSystem.update();

		for(Character character : this.characters){
			character.update();
		}
	}
	private void draw(){
		//Dibujamos el fondo del mapa
		this.player.getMap().DrawBackground(this.cameraSystem);

		//Dibujamos al jugador
		player.draw();

		for(Character character : this.characters){
			if(character.getMap() == this.player.getMap()){
				character.draw();
			}
		}

		//Dibujamos la parte "superior"
		this.player.getMap().DrawForeground();

		//Sistema de notificaciones
		this.notificationsSystem.draw();

		this.player.getInventorySystem().draw();

		//Hacemos que la cámara se actualice
		cameraSystem.draw();
	}

	@Override
	public boolean keyDown(int keycode) {
		Vector2 newDirection = player.getDir();

		if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
        	newDirection.add(DIRECTION_LEFT);
        else if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
        	newDirection.add(DIRECTION_RIGHT);
        else if(keycode == Input.Keys.UP || keycode == Input.Keys.W)
        	newDirection.add(DIRECTION_UP);
        else if(keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
        	newDirection.add(DIRECTION_DOWN);
		else if(keycode == Input.Keys.I || keycode == Input.Keys.A)
			this.player.getInventorySystem().isVisible = !this.player.getInventorySystem().getIsVisible();

        this.player.setDir(newDirection);

        if(keycode == Input.Keys.T){
            this.notificationsSystem.addNotification("Collide: " + !this.player.getCollideState());
            this.player.setCollideState(!this.player.getCollideState());
        }


        //Running
		if(keycode == Input.Keys.SHIFT_LEFT){
			this.player.setIsRunning(true);
		}

		if(keycode == Input.Keys.G){
        	this.player.throwSelectedItem();
		}
		if(keycode == Input.Keys.Q){
        	this.player.selectNextItem();
		}
		if(keycode == Input.Keys.SPACE){
			this.player.useSelectedItem();
		}
		if(keycode == Input.Keys.E){
			Item closestItem = this.player.getMap().getClosestItemTo(this.player.getCenterPos());
			if(closestItem != null){
				float distance = closestItem.getCenterPos().dst(this.player.getCenterPos());
				if(distance <= DISTANCE_USEGROUND_ITEM){
					closestItem.useGround(this.player);
				}
			}
		}
		if(keycode == Input.Keys.R){
			this.player.getMap().reloadMap();
		}
		// Zoom
        if(keycode == Input.Keys.Z) cameraSystem.proportionalZoom(-0.5f);
        if(keycode == Input.Keys.X) cameraSystem.proportionalZoom(0.5f);

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
        //player.setDir(new Vector2(0,0));
		Vector2 newDirection = player.getDir();

		if(keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
			newDirection.sub(DIRECTION_LEFT);
		else if(keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
			newDirection.sub(DIRECTION_RIGHT);
		else if(keycode == Input.Keys.UP || keycode == Input.Keys.W)
			newDirection.sub(DIRECTION_UP);
		else if(keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
			newDirection.sub(DIRECTION_DOWN);

		this.player.setDir(newDirection);


		//Running
		if(keycode == Input.Keys.SHIFT_LEFT){
			this.player.setIsRunning(false);
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.notificationsSystem.addNotification("xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd ");
		return false;
    }

    @Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	//GETTERS
	public Player getPlayer(){ return player; }
	public MapSystem getMapSystem() {return mapSystem;}
	public CameraSystem getCameraSystem() {return cameraSystem;}
	public TriggersSystem getTriggersSystem() {return triggersSystem;}
	public NotificationsSystem getNotificationsSystem() {
		return notificationsSystem;
	}
	public SoundsSystem getSoundsSystem() {
		return this.soundsSystem;
	}

    public Vector2 getRandomDirection(){
		return this.directions[new Random().nextInt(this.directions.length)];
	}

	public BitmapFont getInventoryFont() {
		return inventoryFont;
	}

	public ArrayList<Character_IA> getCharacters() {
		return this.characters;
	}

	public static String formatToFilePath(String mapName){
		return "maps" + File.separator + mapName + ".tmx";
	}

	public BitmapFont getNotificationsFont() {
		return notificationsFont;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public HashMap<String, Class> getAvailableItems() {
		return this.availableItems;
	}

	public void addCharacter(Character_IA character) {
		this.characters.add(character);
	}

	public static Vector2 getDirectionFromString(String str){
		switch(str){
			case "down":
				return DIRECTION_DOWN;

			case "right":
				return DIRECTION_RIGHT;

			case "left":
				return DIRECTION_LEFT;

			case "up":
				return DIRECTION_UP;
			default:
				return new Vector2();
		}
	}

	public HashMap<String, Class> getAvailableCharacters_IA() {
		return this.availableCharacters_IA;
	}
}