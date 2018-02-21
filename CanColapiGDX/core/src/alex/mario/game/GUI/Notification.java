package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Notification_L;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Notification extends Notification_L {
    public static final int DEFAULT_NOTIFICATION_TIME = 2000;
    public static final int NOTIFICATION_BORDER_WIDTH = 5;
    public static final int NOTIFICATION_PADDING = 5;
    private GlyphLayout notificationLayout;
    public Notification(String message){
        super(message);
        this.notificationLayout = new GlyphLayout();
    }
    public float getNotificationHeight(){
        return this.notificationLayout.height + NOTIFICATION_PADDING * 2 + NOTIFICATION_BORDER_WIDTH * 2;
    }
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Vector2 pos, Vector2 size)
    {
        shapeRenderer.begin();
        //Calculate text width-height
        this.notificationLayout.setText(font, this.message, Color.WHITE, size.x - 10 - NOTIFICATION_BORDER_WIDTH * 2, Align.left, true);

        //Border
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(
                pos.x, pos.y,
                size.x, notificationLayout.height  + NOTIFICATION_BORDER_WIDTH * 2 + NOTIFICATION_PADDING * 2
        );

        //Bakcground
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(
                pos.x + NOTIFICATION_BORDER_WIDTH, pos.y + NOTIFICATION_BORDER_WIDTH,
                size.x -  NOTIFICATION_BORDER_WIDTH * 2, notificationLayout.height + NOTIFICATION_PADDING * 2
        );
        shapeRenderer.end();

        spriteBatch.begin();
        //Message
        font.draw(spriteBatch, this.message,
                NOTIFICATION_BORDER_WIDTH + 2, pos.y + notificationLayout.height + NOTIFICATION_BORDER_WIDTH + NOTIFICATION_PADDING,
                size.x - 10 - NOTIFICATION_BORDER_WIDTH * 2, Align.left, true);

        spriteBatch.end();
    }
}
