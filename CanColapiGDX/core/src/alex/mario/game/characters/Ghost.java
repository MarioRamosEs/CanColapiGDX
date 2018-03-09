package alex.mario.game.characters;

import alex.mario.game.GUI.Character_IA;
import alex.mario.game.GUI.Map;
import alex.mario.game.GUI.TexturesSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ghost extends Character_IA {
    public Ghost(MyGdxGame game, Map map, boolean chasing) {
        super(game, map, chasing);
        this.collides = false;
        this.minVel = 2.5f;
        this.playerFrames = TextureRegion.split(TexturesSystem.getTexture("ghost.png"), 48, 64);
        this.positionToChase = this.game.getPlayer().getPos();
    }

    @Override
    public void think() {
        this.positionToChase = this.game.getPlayer().getPos();
        super.think();

        if (this.map != this.game.getPlayer().getMap()) {
            return;
        }

        if (!this.game.getPlayer().isHiding()) {
            if (this.distWithPlayer < 120 && this.distWithPlayer > 50) {
                if (this.game.getPlayer().trigger("seenByGhost-" + this.id) && !chasing) {
                    this.game.getNotificationsSystem().addNotification("Has sido visto por un fantasma... ¡corre!");
                    this.chasing = true;
                }
            } else {
                this.game.getPlayer().unTrigger("seenByGhost-" + this.id, false);
            }
        }

        if (chasing && this.distWithPlayer < 50) {
            if (this.game.getPlayer().trigger("touchedByGhost-" + this.id)) {
                this.game.getNotificationsSystem().addNotification("Has sido tocado por un fantasma.");
                this.game.gameOver();
            } else {
                this.game.getPlayer().unTrigger("touchedByGhost-" + this.id, false);
            }
        }
    }

    @Override
    public void reset(){
        this.chasing = false;
        super.reset();
    }

    @Override
    public void update() {
        this.think();
        super.update();
    }

    @Override
    public void getDamage(int damageQty) {
        this.game.getCharacters().remove(this);
    }
}
