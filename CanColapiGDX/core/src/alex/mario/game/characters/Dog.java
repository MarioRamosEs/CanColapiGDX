package alex.mario.game.characters;

import alex.mario.game.GUI.*;
import alex.mario.game.GUI.Character;
import alex.mario.game.MyGdxGame;
import alex.mario.game.objects.Bone;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Dog extends Character_IA {
    public Dog(MyGdxGame game, Map map, boolean chasing) {
        super(game, map, chasing);
        this.direction = new Vector2(0, 0);
        this.playerFrames = TextureRegion.split(TexturesSystem.getTexture("dog.png"), 48, 64);
        this.collides = false;
    }

    @Override
    public void think() {
        super.think();

        if (this.map != this.game.getPlayer().getMap()) {
            return;
        }

        Bone bone = this.searchBone();
        if (bone != null) { //If exists any bone in the map
            this.positionToChase = bone.getPos();
            this.chasing = true;
        } else {
            this.chasing = false;
        }

        if (this.distWithPlayer <= 50) {
            if (this.game.getPlayer().trigger("touchedByDog-" + this.id)) {
                this.game.getNotificationsSystem().addNotification("Has sido atacado por un perro...");
                this.game.gameOver();
            }
        } else {
            this.game.getPlayer().unTrigger("touchedByDog-" + this.id, false);
        }

    }

    public Bone searchBone() {
        for (Item item : this.map.getMapItems()) {
            if (item instanceof Bone) {
                return (Bone) item;
            }
        }
        return null;
    }

    @Override
    public void update() {
        this.think();
        super.update();
    }
}
