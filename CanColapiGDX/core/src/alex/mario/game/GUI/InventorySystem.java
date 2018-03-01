package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iSystem;
import alex.mario.game.LOGIC.InventorySystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class InventorySystem extends InventorySystem_L implements iSystem {
    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;
    public boolean isVisible = false;
    protected BitmapFont font;
    public InventorySystem(MyGdxGame game){
        super(game);
        this.font = this.game.getInventoryFont();
        this.spriteBatch = this.game.getSpriteBatch();
        this.shapeRenderer = this.game.getShapeRenderer();
    }

    public void draw(){
        if(!this.isVisible){return;}
        int i = 0;
        for(Item item : this.items){

            item.draw(
                    this.shapeRenderer,
                    this.spriteBatch,
                    this.font,
                    new Vector2(40 + i * 90, this.game.getCameraSystem().getScreenSize().y - 90),
                    new Vector2(80, 80));
            i++;
        }
    }
    public void show(){
        this.isVisible = true;
    }

    public boolean getIsVisible() {
        return isVisible;
    }
}
