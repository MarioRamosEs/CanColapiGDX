package alex.mario.game.characters;

import alex.mario.game.GUI.Character;
import alex.mario.game.GUI.Map;
import alex.mario.game.MyGdxGame;

public class Player extends Character {
    public Player(MyGdxGame game, Map map){
        super(game, map);
        System.out.println("CREANDO PLAYER");
    }
}
