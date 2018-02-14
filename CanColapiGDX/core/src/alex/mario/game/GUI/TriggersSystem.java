package alex.mario.game.GUI;

import com.badlogic.gdx.maps.MapProperties;

public class TriggersSystem {

    MyGdxGame main;

    TriggersSystem(MyGdxGame main) {
        this.main = main;
    }

    public void trigger(MapProperties mp){
        //Iterator<String> keys = mp.getKeys();
        //Iterator<Object> values = mp.getValues();
        //System.out.println(keys.next().toString());
        //System.out.println(values.next().toString());

        if(mp.containsKey("EnviarMensaje")) System.out.println(mp.get("EnviarMensaje"));

        if(mp.containsKey("CambioMapa")) main.loadMap((String)mp.get("CambioMapa"));
    }
}
