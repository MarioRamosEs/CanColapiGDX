package alex.mario.game.LOGIC;

import alex.mario.game.GUI.SoundsSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundSystem_L {
    protected MyGdxGame game;
    public static HashMap<String, Sound> soundsPlaying = new HashMap<String, Sound>();

    public SoundSystem_L(MyGdxGame game){
        this.game = game;
    }

    public void play(String sound){
        if(!soundsPlaying.containsKey(sound)) { //No solapar dos veces el mismo audio
            Sound tempSound = SoundsSystem.getSound(sound);
            tempSound.play();
            soundsPlaying.put(sound, tempSound);
        }
        //Sound mp3Sound = Gdx.audio.newSound(Gdx.files.internal("sounds/churchbell.mp3"));
        //mp3Sound.play();
    }

    public void stop(String sound){
        Sound tempSound = soundsPlaying.get(sound);
        tempSound.stop();
        soundsPlaying.remove(sound);
    }

    public void stopAll(){
        for(Map.Entry<String, Sound> entry : soundsPlaying.entrySet()) {
            String key = entry.getKey();

            Sound tempSound = soundsPlaying.get(key);
            tempSound.stop();
            soundsPlaying.remove(key);
        }
    }
}
