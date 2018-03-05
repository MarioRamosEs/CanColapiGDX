package alex.mario.game.characters;

import alex.mario.game.GUI.Character_IA;
import alex.mario.game.GUI.Map;
import alex.mario.game.MyGdxGame;

public class Ghost extends Character_IA {
    public Ghost(MyGdxGame game, Map map, boolean chasing){
        super(game, map, chasing);
        this.collides = false;
        this.minVel = 2.5f;
        //this.direction = this.game.getRandomDirection();
    }
    @Override
    public void think(){
        if(!this.game.getPlayer().isHiding()) {
            if (this.getCenterPos().dst(this.game.getPlayer().getCenterPos()) <= 100) {
                if (this.game.getPlayer().trigger("seenByGhost-" + this.id)) {
                    this.game.getNotificationsSystem().addNotification("Has sido visto por un fantasma...");
                }
            } else {
                this.game.getPlayer().unTrigger("seenByGhost-" + this.id, false);
            }
        }
        super.think();

    }
    @Override
    public void update(){
        this.think();
        super.update();
    }

    @Override
    public void getDamage(int damageQty){
        this.game.getCharacters().remove(this);
    }
}
