package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Notification;
import alex.mario.game.MyGdxGame;

public class Notification_L {
    protected String message = "";
    protected long startTime = 0;
    protected long endTime = 0;

    public Notification_L(String message, long startTime, long endTime) {
        this.startTime = startTime;
        this.message = message;
        this.endTime = endTime;
    }

    public Notification_L(String message) {
        this.message = message;
        this.startTime = System.currentTimeMillis();
        this.endTime = System.currentTimeMillis() + Notification.DEFAULT_NOTIFICATION_TIME;
        //this.startTime = (int)game.gameTime.TotalGameTime.Seconds;
    }

    public String getMessage() {
        return this.message;
    }

    public void setEndTime(int time) {
        this.endTime = time;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public boolean shouldDelete() {
        return endTime < System.currentTimeMillis();
    }

}
