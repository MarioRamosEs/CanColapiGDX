package alex.mario.game.LOGIC;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class Item_L {
    protected String name;
    protected String description;
    protected Boolean isPicked = false;
    protected Vector2 pos;
    protected Vector2 size;
    protected boolean isPassable = false;

    public Rectangle getRectangle() {
        return new Rectangle(this.pos.x, this.pos.y, this.size.x, this.size.y);
    }

    public boolean isPassable() {
        return this.isPassable;
    }
}
