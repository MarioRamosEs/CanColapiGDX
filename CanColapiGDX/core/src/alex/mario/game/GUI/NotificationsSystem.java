package alex.mario.game.GUI;

import alex.mario.game.LOGIC.NotificationsSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class NotificationsSystem extends NotificationsSystem_L {
    private BitmapFont font;
    public NotificationsSystem(MyGdxGame game){
        super(game);
        this.font = this.game.getMainFont();
        font.setColor(Color.WHITE);

        this.shapeRenderer.setAutoShapeType(true);

    }


    public void draw(){
        int i = 0;
        float lastNotificationHeight = 0;

        for(Notification notification : this.notifications){
            notification.draw(
                    this.shapeRenderer,
                    this.spriteBatch,
                    this.font,
                    new Vector2(0, lastNotificationHeight),
                    new Vector2(this.game.getCameraSystem().getScreenSize().x, 0));
            lastNotificationHeight += notification.getNotificationHeight();
            i++;
        }
    }

}
