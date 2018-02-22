package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Notification;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class NotificationsSystem_L {
    protected MyGdxGame game;
    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;
    protected ArrayList<Notification> notifications;
    public NotificationsSystem_L(MyGdxGame game){
        this.game = game;
        this.spriteBatch = this.game.getSpriteBatch();
        this.shapeRenderer = this.game.getShapeRenderer();
        this.notifications = new ArrayList<Notification>();
        this.spriteBatch.setProjectionMatrix(game.getCameraSystem().getCamera().combined);
    }

    public void update(){

        for(Notification notification : this.notifications){
            if(notification.shouldDelete()){
                this.notifications.remove(notification);
                break;
            }
        }
    }

    public void addNotification(String message){
        this.notifications.add(new Notification(message));

    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
