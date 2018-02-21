package alex.mario.game.GUI;

import alex.mario.game.LOGIC.NotificationsSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class NotificationsSystem extends NotificationsSystem_L {
    private BitmapFont font;
    public NotificationsSystem(MyGdxGame game, BitmapFont font){
        super(game);
        this.font = font;
        font.setColor(Color.WHITE);

        this.shapeRenderer.setAutoShapeType(true);

    }


    public void draw(){
        int i = 0;
        int notificationHeight = 40;

        for(Notification notification : this.notifications){
            notification.draw(
                    this.shapeRenderer,
                    this.spriteBatch,
                    this.font,
                    new Vector2(0,0 + (i * (notificationHeight + 5))),
                    new Vector2(this.game.getCameraSystem().getScreenSize().x, notificationHeight));
            i++;
        }
        //font.draw(spriteBatch, "Hello World", 200, 200);
    }

}
