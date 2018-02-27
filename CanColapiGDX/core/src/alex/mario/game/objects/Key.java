package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Key extends Item {
    protected String keyCode;
    public Key(RectangleMapObject rectangleMapObject){
        super(rectangleMapObject);
        this.name = "Llave";
        this.texture = TexturesSystem.getTexture("pokeBall.png");
        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());
        this.keyCode = rectangleMapObject.getProperties().get("keyCode").toString();
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Vector2 pos, Vector2 size)
    {
        shapeRenderer.begin();
        //Calculate text width-height
        this.notificationLayout.setText(font, this.name + " (" + this.keyCode + ")", Color.WHITE, size.x - 10, Align.left, true);

        //Bakcground
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(
                pos.x, pos.y,
                size.x, size.y
        );

        shapeRenderer.end();

        spriteBatch.begin();
        //Message
        font.draw(spriteBatch, this.name + " (" + this.keyCode + ")",
                pos.x + 5, pos.y + notificationLayout.height + 5,
                size.x - 5, Align.center, true);

        spriteBatch.end();
    }
    public String getKeyCode(){
        return this.keyCode;
    }
}
