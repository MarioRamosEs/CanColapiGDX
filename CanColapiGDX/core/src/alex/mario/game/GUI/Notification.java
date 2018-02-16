package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Notification_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.math.Vector2;

public class Notification extends Notification_L {
    public static final int DEFAULT_NOTIFICATION_TIME = 2000;
    public Notification(MyGdxGame game, String message, int endTime){
        super(game, message, endTime);
    }
    public void Draw(Vector2 pos, Vector2 size)
    {
        //utilDraw.drawRectangle(spriteBatch, game.lineTexture, new Rectangle(pos.ToPoint(), size.ToPoint()), Color.Red);
        //utilDraw.drawRectangle(spriteBatch, game.lineTexture, new Rectangle(pos.ToPoint(), size.ToPoint()), Color.Black);
        //spriteBatch.DrawString(game.defaultFont, this.message, pos + new Vector2(10, 10), Color.Black);
        //spriteBatch.DrawString(game.defaultFont, (this.endTime - (int)game.gameTime.TotalGameTime.TotalMilliseconds).ToString(), pos + new Vector2(-100 + size.X, 0), Color.Black);
    }
}
