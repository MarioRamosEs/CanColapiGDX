package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class CharacterSpawner extends Item {
    private int spawnEvery;
    private int quantity;
    private Long lastSpawn;
    public CharacterSpawner(MyGdxGame game, Map map, RectangleMapObject rectangleMapObject){
        super(game, map, rectangleMapObject);
        this.name = rectangleMapObject.getProperties().get("name", "CharacterSpawner", String.class);
        this.spawnEvery = rectangleMapObject.getProperties().get("spawnEvery", 5000, int.class);
        this.quantity = rectangleMapObject.getProperties().get("quantity", 2, int.class);

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
        if( (lastSpawn + this.spawnEvery) < System.currentTimeMillis()){
            this.lastSpawn = System.currentTimeMillis();
            this.game.addCharacter(new Character(this.game, this.map));
        }
    }
}
