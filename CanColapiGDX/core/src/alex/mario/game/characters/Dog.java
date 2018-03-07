package alex.mario.game.characters;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Character_IA;
import alex.mario.game.GUI.Map;
import alex.mario.game.MyGdxGame;
import alex.mario.game.objects.Bone;
import com.badlogic.gdx.math.Vector2;

public class Dog extends Character_IA {
    public Dog(MyGdxGame game, Map map){
        super(game, map, false);
        this.direction = new Vector2(0, 0);
    }

    @Override
    public void think(){
        super.think();

        if(this.map != this.game.getPlayer().getMap()){return;}

        Bone bone = this.map.getBone();
        if(bone != null){ //If exists any bone in the map
            this.positionToChase = bone.getPos();
            this.chasing = true;
        }else{
            this.chasing = false;
        }

        if (this.getCenterPos().dst(this.game.getPlayer().getCenterPos()) <= 10) {
            if (this.game.getPlayer().trigger("seenByGhost-" + this.id)) {
                this.game.getNotificationsSystem().addNotification("Has sido atacado por un perro...");
            }
        } else {
            this.game.getPlayer().unTrigger("seenByGhost-" + this.id, false);
        }
    }
    @Override
    public void update(){
        this.think();
        super.update();
    }
}
