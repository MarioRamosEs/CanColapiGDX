package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Notification_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.awt.*;

public class Notification extends Notification_L {
    public static final int DEFAULT_NOTIFICATION_TIME = 2000;
    public static final int borderWidth = 5;
    public Notification(String message){
        super(message);
    }
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Vector2 pos, Vector2 size)
    {
        shapeRenderer.begin();
        //Border
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(pos.x, pos.y, size.x, size.y);

        //Bakcground
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(pos.x + borderWidth, pos.y + borderWidth, size.x -  borderWidth * 2, size.y - borderWidth * 2);
        shapeRenderer.end();

        spriteBatch.begin();
        //Message
        font.draw(spriteBatch, "Hello World", borderWidth + 2, pos.y + (size.y / 2) + borderWidth, size.x - 10 - borderWidth * 2, Align.left, true);
        spriteBatch.end();
    }
}
