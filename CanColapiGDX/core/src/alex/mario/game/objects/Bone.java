package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Bone extends Item {
    protected String keyCode;
    public Bone(MyGdxGame game, Map map, RectangleMapObject rectangleMapObject){
        super(game, map, rectangleMapObject);
        this.name = rectangleMapObject.getProperties().get("name", "Llave", String.class);

        this.texture = TexturesSystem.getTexture(rectangleMapObject.getProperties().get("texture", "pokeBall.png", String.class));
        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());
        this.keyCode = rectangleMapObject.getProperties().get("keyCode").toString();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Vector2 pos, Vector2 size){
        shapeRenderer.begin();
        //Calculate text width-height
        this.itemNameLayout.setText(font, this.name + " (" + this.keyCode + ")", Color.WHITE, size.x - 10, Align.left, true);

        //Bakcground
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(
                pos.x, pos.y,
                size.x, size.y
        );

        shapeRenderer.end();

        spriteBatch.begin();
        spriteBatch.draw(texture, pos.x + size.x / 2 - this.texture.getWidth() / 2, pos.y + size.y / 2 - this.texture.getHeight() / 2 + 15);

        //Message
        font.draw(spriteBatch, this.name + " (" + this.keyCode + ")",
                pos.x + 5, pos.y + itemNameLayout.height + 5,
                size.x - 5, Align.center, true);

        spriteBatch.end();
    }

    @Override
    public void use(Character character){

    }

    public String getKeyCode(){
        return this.keyCode;
    }
}