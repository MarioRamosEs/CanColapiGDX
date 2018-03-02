package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.MyGdxGame;
import alex.mario.game.characters.Dog;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class CharacterSpawner extends Item {
    private String classCharacter;
    private boolean active = true;
    private int spawnEvery;
    private int quantity;
    private Long lastSpawn;
    private int quantity_spawned = 0;
    public CharacterSpawner(MyGdxGame game, Map map, RectangleMapObject rectangleMapObject){
        super(game, map, rectangleMapObject);
        this.name = rectangleMapObject.getProperties().get("name", "CharacterSpawner", String.class);
        this.spawnEvery = rectangleMapObject.getProperties().get("spawnEvery", 5000, int.class);
        this.quantity = rectangleMapObject.getProperties().get("quantity", 2, int.class);
        this.classCharacter = rectangleMapObject.getProperties().get("classCharacter", "UNDEFINED", String.class);

        this.size = MyGdxGame.DEFAULT_TILE_SIZE;

        this.lastSpawn = 0l;
    }

    @Override
    public void useGround(Character character){
    }
    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Vector2 pos, Vector2 size)
    {
        return;
    }
    @Override
    public void drawGround(SpriteBatch spriteBatch, Camera camera){
        return;
    }

    @Override
    public void touch(Character_L character){

    }
    @Override
    public void update(){
        if(!active){return;}

        if( (lastSpawn + this.spawnEvery) < System.currentTimeMillis()){
            System.out.println("SPAWNED");
            this.lastSpawn = System.currentTimeMillis();
            Character character = new Dog(this.game, this.map);
            character.setPos(this.pos);
            System.out.println(this.name);
            this.game.addCharacter(character);
            this.quantity_spawned++;
            if(this.quantity_spawned > this.quantity){
                this.active = false;
            }
        }
    }
}
