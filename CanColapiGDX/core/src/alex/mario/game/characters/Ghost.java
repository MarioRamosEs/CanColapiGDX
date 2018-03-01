package alex.mario.game.characters;

import alex.mario.game.GUI.Character_IA;
import alex.mario.game.GUI.Map;
import alex.mario.game.MyGdxGame;

public class Ghost extends Character_IA {
    public Ghost(MyGdxGame game, Map map){
        super(game, map);

        //this.direction = this.game.getRandomDirection();
    }

    @Override
    public void update(){
        this.think();
        super.update();
    }
}
