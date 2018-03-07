package alex.mario.game.objects;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Item;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;

public class Ouija extends Item {

    public Ouija(MyGdxGame game, Map map, RectangleMapObject rectangleMapObject) {
        super(game, map, rectangleMapObject);
        this.name = "Ouija";
        this.texture = TexturesSystem.getTexture("ouija.png");
        //System.out.println();
    }

    @Override
    public void use(Character character){
        Map map = character.getMap();
        Vector2 direction = character.getLastDirVector2().cpy();

        Bullet bullet = new Bullet(this.game, map, new RectangleMapObject());
        bullet.setDirection(direction);
        bullet.setPos(character.getCenterPos().cpy().add(direction.scl(2,2)));
        bullet.setOwner(character);
        map.addItem(bullet);
    }


}
