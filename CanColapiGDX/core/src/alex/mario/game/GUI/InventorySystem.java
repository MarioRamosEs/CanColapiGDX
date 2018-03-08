package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iSystem;
import alex.mario.game.LOGIC.InventorySystem_L;
import alex.mario.game.GUI.Character;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class InventorySystem extends InventorySystem_L implements iSystem {
    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;
    public boolean isVisible = false;
    protected BitmapFont font;
    public InventorySystem(MyGdxGame game, Character character){
        super(game, character);
        this.font = this.game.getInventoryFont();
        this.spriteBatch = this.game.getSpriteBatch();
        this.shapeRenderer = this.game.getShapeRenderer();
    }

    public void draw(){
        if(!this.isVisible){return;}
        int i = 0;
        Item selectedItem = this.character.getSelectedItem();
        for(Item item : this.items){
            Vector2 pos = new Vector2(40 + i * 110, this.game.getCameraSystem().getScreenSize().y - 110);
            Vector2 size = new Vector2(100, 80);
            item.draw(
                    this.shapeRenderer,
                    this.spriteBatch,
                    this.font,
                    pos,
                    size
            );

            if(item == selectedItem){
                shapeRenderer.begin();
                //Bakcground
                shapeRenderer.set(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.GREEN);
                shapeRenderer.rect(
                        pos.x, pos.y,
                        size.x, size.y
                );

                shapeRenderer.end();
            }
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
