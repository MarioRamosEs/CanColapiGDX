package alex.mario.game.characters;

import alex.mario.game.GUI.Character;
import alex.mario.game.MyGdxGame;

public class Dog extends Character {
    public Dog(MyGdxGame game){
        super(game);
        this.direction = this.game.getRandomDirection();
    }

    @Override
    public void update(){
        super.update();

    }
}
