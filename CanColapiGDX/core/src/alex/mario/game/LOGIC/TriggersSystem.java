package alex.mario.game.LOGIC;

import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapProperties;

public class TriggersSystem {

    MyGdxGame game;

    public TriggersSystem(MyGdxGame game) {
        this.game = game;
    }

    public void trigger(MapProperties mp){
        //Iterator<String> keys = mp.getKeys();
        //Iterator<Object> values = mp.getValues();
        //System.out.println(keys.next().toString());
        //System.out.println(values.next().toString());

        if(mp.containsKey("EnviarMensaje")) System.out.println(mp.get("EnviarMensaje"));

        if(mp.containsKey("CambioMapa")) this.game.loadMap((String)mp.get("CambioMapa"));
    }
}
