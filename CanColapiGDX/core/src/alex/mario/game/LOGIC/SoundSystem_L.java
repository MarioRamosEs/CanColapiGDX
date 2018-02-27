package alex.mario.game.LOGIC;

import alex.mario.game.GUI.SoundsSystem;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundSystem_L {
    protected MyGdxGame game;
    public static HashMap<String, Music> soundsPlaying = new HashMap<String, Music>();

    public SoundSystem_L(MyGdxGame game){
        this.game = game;
    }

    public void play(String sound){
       cleanSoundsPlaying();
        if(!soundsPlaying.containsKey(sound)) { //No solapar dos veces el mismo audio
            Music tempSound = SoundsSystem.getMusic(sound);
            tempSound.play();
            soundsPlaying.put(sound, tempSound);
        }
    }

    public void playLoop(String sound){
        cleanSoundsPlaying();
        if(!soundsPlaying.containsKey(sound)) { //No solapar dos veces el mismo audio
            Music tempSound = SoundsSystem.getMusic(sound);
            tempSound.setLooping(true);
            tempSound.play();
            soundsPlaying.put(sound, tempSound);
        }
    }

    public void stop(String sound){
        cleanSoundsPlaying();
        if(soundsPlaying.containsKey(sound)) {
            Music tempSound = soundsPlaying.get(sound);
            tempSound.stop();
            soundsPlaying.remove(sound);
        }
    }

    public void stopAll(){
        cleanSoundsPlaying();

        ArrayList<String> cue = new ArrayList<>();
        for(Map.Entry<String, Music> entry : soundsPlaying.entrySet()) {
            String key = entry.getKey();

            Music tempSound = soundsPlaying.get(key);
            tempSound.stop();
            cue.add(key);
        }
        remove(cue);
    }

    private void cleanSoundsPlaying(){ //Elimina los audios que no están sonando
        ArrayList<String> cue = new ArrayList<>();
        for(Map.Entry<String, Music> entry : soundsPlaying.entrySet()) {
            String key = entry.getKey();
            Music tempSound = soundsPlaying.get(key);

            if(!tempSound.isPlaying()) cue.add(key); //soundsPlaying.remove(key);
        }
        remove(cue);
    }

    private void remove(ArrayList<String> cue){
        for(String element : cue){
            soundsPlaying.remove(element);
            //dispose
        }
    }
}
