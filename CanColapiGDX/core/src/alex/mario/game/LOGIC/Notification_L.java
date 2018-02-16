package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Notification;
import alex.mario.game.MyGdxGame;

public class Notification_L {
    private String message = "";
    private MyGdxGame game;
    private int startTime = 0;
    private int endTime = 0;

    public Notification_L(MyGdxGame game, String message, int endTime)
    {
        this.message = message;
        this.game = game;
        this.endTime = endTime;

    }
    public Notification_L(MyGdxGame game, String message)
    {
        this.message = message;
        this.game = game;
        this.endTime = Notification.DEFAULT_NOTIFICATION_TIME;
        //this.startTime = (int)game.gameTime.TotalGameTime.Seconds;
    }
    public String getMessage()
    {
        return this.message;
    }

    public void setEndTime(int time)
    {
        this.endTime = time;
    }
    public int getEndTime()
    {
        return this.endTime;
    }
}
