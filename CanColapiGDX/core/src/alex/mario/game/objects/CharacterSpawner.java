package alex.mario.game.objects;

import alex.mario.game.GUI.*;
import alex.mario.game.GUI.Character;
import alex.mario.game.LOGIC.Character_L;
import alex.mario.game.MyGdxGame;
import alex.mario.game.characters.Dog;
import alex.mario.game.characters.Ghost;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        this.classCharacter = rectangleMapObject.getProperties().get("character_IA", "UNDEFINED", String.class);

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
            this.lastSpawn = System.currentTimeMillis();

           //items.add(new Key(isPicked, new Vector2(rectangle.getX(), rectangle.getY())));

            //REFLECTION:
            // http://tutorials.jenkov.com/java-reflection/constructors.html
            //Se llama a la función trigger para cada llave, incluimos todas las propiedades por si hace falta información
            //extra
            Character_IA character = null;
            try {
                Class cl = game.getAvailableCharacters_IA().get(this.classCharacter);
                Constructor constructor = cl.getConstructor(new Class[]{MyGdxGame.class, Map.class});
                character = (Character_IA)constructor.newInstance(this.game, this.map);


            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
            }
            if(character == null){
                System.out.println("ERROR SPAWNING");
                return;
            }
            character.setPos(this.getCenterPos());
            this.game.addCharacter(character);

            this.quantity_spawned++;

            if(this.quantity_spawned >= this.quantity){
                this.active = false;
            }
        }
    }
}
